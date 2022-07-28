package net.rmnad.minecraft.forge.serverstatsstream.services;

import net.minecraft.core.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.dimension.DimensionType;
import net.rmnad.minecraft.forge.serverstatsstream.ServerStatsStream;
import net.rmnad.minecraft.forge.serverstatsstream.models.DimensionTickInfo;
import net.rmnad.minecraft.forge.serverstatsstream.models.ServerReport;
import net.rmnad.minecraft.forge.serverstatsstream.models.TickInfo;
import net.rmnad.minecraft.forge.serverstatsstream.Config;

import java.util.ArrayList;
import java.util.List;

public class ReportingThread extends Thread {

    private final MinecraftServer server;
    public ReportingThread(MinecraftServer server) {
        this.server = server;
    }

    public void serverStopping() throws InterruptedException {

        ServerStatsStream.LOGGER.info("Shutting down reporting thread");

        this.join();
    }

    @Override
    public void run() {
        while(server.isRunning()) {
            ServerReport report = GetReport(server);
            SendReport(report);

            try {
                Thread.sleep(Config.COMMON.REPORT_INTERVAL.get());
            } catch (InterruptedException ignored) { }
        }
    }

    public void SendReport(ServerReport report) {
        ServerStatsStream.LOGGER.info(report.toString());
    }

    private ServerReport GetReport(MinecraftServer server) {
        // Build report
        ServerReport report = new ServerReport();
        report.setServerName(Config.COMMON.SERVER_NAME.get());
        report.setTickInfo(getTickInfo(server));
        report.setPlayersOnline(server.getPlayerCount());
        report.setTotalPlayerSlots(server.getMaxPlayers());

        return report;
    }

    private TickInfo getTickInfo(MinecraftServer server) {
        List<DimensionTickInfo> dimensionTickInfoList;
        dimensionTickInfoList = new ArrayList<>();

        for (ServerLevel dim : server.getAllLevels()) {
            dimensionTickInfoList.add(getDimensionTickInfo(server, dim));
        }

        double meanTickTime = mean(server.tickTimes) * 1.0E-6D;
        double meanTPS = Math.min(1000.0 / meanTickTime, 20);

        return new TickInfo(meanTickTime, meanTPS, dimensionTickInfoList);
    }

    private static DimensionTickInfo getDimensionTickInfo(MinecraftServer server, ServerLevel dim)
    {
        long[] times = server.getTickTime(dim.dimension());

        if (times == null) // Null means the world is unloaded. Not invalid. That's taken car of by DimensionArgument itself.
            times = new long[]{0};

        final Registry<DimensionType> reg = server.registryAccess().registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        double worldTickTime = mean(times) * 1.0E-6D;
        double worldTPS = Math.min(1000.0 / worldTickTime, 20);

        return new DimensionTickInfo(reg.getId(dim.dimensionType()), worldTickTime, worldTPS);
    }

    private static long mean(long[] values)
    {
        long sum = 0L;
        for (long v : values)
            sum += v;
        return sum / values.length;
    }
}
