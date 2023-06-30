package org.locationbookmark;

import org.bukkit.entity.Entity;

public class Location {
    private double x;
    private double y;
    private double z;
    private String playerName;
    private String locationName;

    public Location(double x, double y, double z , String player, String locationName){
        this.x = x;
        this.y = y;
        this.z = z;
        this.playerName = player;
        this.locationName = locationName;
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getZ() {
        return this.z;
    }
    public String getPlayer() {
        return this.playerName;
    }
    public String getLocationName() {
        return this.locationName;
    }
}
