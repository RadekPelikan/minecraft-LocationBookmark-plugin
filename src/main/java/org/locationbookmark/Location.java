package org.locationbookmark;

import java.util.ArrayList;

public class Location {
    private final int id;
    private final String playerName;
    private final String locationName;
    private final double x;
    private final double y;
    private final double z;
    private final int parentLocationId;
    private Location parentLocation;
    private ArrayList<Location> childLocations;

    public Location(int id, String playerName, String locationName, double x, double y, double z, int parentLocationId) {
        this.id = id;
        this.playerName = playerName;
        this.locationName = locationName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.parentLocationId = parentLocationId;
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
    public String getName() {
        return this.name;
    }
}
