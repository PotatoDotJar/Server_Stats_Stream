package net.rmnad.minecraft.forge.serverstatsstream.models;

//import com.influxdb.annotations.Column;
//import com.influxdb.annotations.Measurement;

import java.time.Instant;

//@Measurement(name = "server_report")
public class ServerReport {

    //@Column(tag = true)
    private String server_name;

    //@Column
    private double mean_mspt;

    //@Column
    private double mean_tps;

    //@Column
    private int players_online;

    //@Column
    private int total_player_slots;

    //@Column(timestamp = true)
    //Instant time;

    public String getServerName() {
        return server_name;
    }

    public void setServerName(String server_name) {
        this.server_name = server_name;
    }

    public double getMeanMspt() {
        return mean_mspt;
    }

    public void setMeanMspt(double mean_mspt) {
        this.mean_mspt = mean_mspt;
    }

    public double getMeanTps() {
        return mean_tps;
    }

    public void setMeanTps(double mean_tps) {
        this.mean_tps = mean_tps;
    }

    public int getPlayersOnline() {
        return players_online;
    }

    public void setPlayersOnline(int players_online) {
        this.players_online = players_online;
    }

    public int getTotalPlayerSlots() {
        return total_player_slots;
    }

    public void setTotalPlayerSlots(int total_player_slots) {
        this.total_player_slots = total_player_slots;
    }

//    public Instant getTime() {
//        return time;
//    }
//
//    public void setTime(Instant time) {
//        this.time = time;
//    }

    @Override
    public String toString() {
        return "ServerReport{" +
                "server_name='" + server_name + '\'' +
                ", mean_mspt=" + mean_mspt +
                ", mean_tps=" + mean_tps +
                ", players_online=" + players_online +
                ", total_player_slots=" + total_player_slots +
                //", time=" + time +
                '}';
    }
}
