package com.example.backend;

import java.util.ArrayList;
import java.util.List;

public class Rider {
    private String rider_id;
    private final Location location;
    private List<Driver> matchedDriversList;

    public Rider(String rider_id, Location location) {
        matchedDriversList = new ArrayList<>();
        this.rider_id = rider_id;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void addNearbyDrivers(List<Driver> matchedDriversList) {
        this.matchedDriversList = matchedDriversList;
    }

    public List<Driver> getNearbyDrivers() {
        return matchedDriversList;
    }

    public String getRiderId() {
        return rider_id;
    }
}
