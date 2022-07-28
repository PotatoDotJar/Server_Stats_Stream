package net.rmnad.minecraft.forge.serverstatsstream;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.rmnad.minecraft.forge.serverstatsstream.services.ReportingThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("serverstatsstream")
public class ServerStatsStream
{
    public static final Logger LOGGER = LogManager.getLogger();
    private static ReportingThread reportingThread;

    public ServerStatsStream() {
        // Load config
        Config.register(ModLoadingContext.get());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        StartSyncThread(event.getServer());
    }

    @SubscribeEvent
    public void onServerStop(ServerStoppingEvent event) {
        LOGGER.info("Server stopping!");
        if(reportingThread != null) {
            try {
                reportingThread.serverStopping();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void StartSyncThread(MinecraftServer server) {
        reportingThread = new ReportingThread(server);
        reportingThread.start();
        LOGGER.info("Reporting Thread Started!");
    }
}
