package net.rmnad.minecraft.forge.serverstatsstream.models;

import java.util.List;

public class TickInfo {

    private double meanTickTime;
    private double meanTps;

    public TickInfo(double meanTickTime, double meanTps) {
        this.meanTickTime = meanTickTime;
        this.meanTps = meanTps;
    }

    public double getMeanTickTime() {
        return meanTickTime;
    }

    public void setMeanTickTime(double meanTickTime) {
        this.meanTickTime = meanTickTime;
    }

    public double getMeanTps() {
        return meanTps;
    }

    public void setMeanTps(double meanTps) {
        this.meanTps = meanTps;
    }


    @Override
    public String toString() {
        return "TickInfo{" +
                "meanTickTime=" + meanTickTime +
                ", meanTps=" + meanTps +
                '}';
    }
}
