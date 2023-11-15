package org.locationbookmark.Interfaces;

import org.locationbookmark.Location;

import java.util.ArrayList;

public interface ILocationRepository {
    String playerName = null;
    ArrayList<Location> getAll();
    Location get(String locationName);
    boolean save(Location location);
    boolean delete(String locationName);
}
