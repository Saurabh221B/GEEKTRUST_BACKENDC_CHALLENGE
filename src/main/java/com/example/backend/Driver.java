package com.example.backend;

public class Driver {

    private final String driver_id;
    private final Location location;


    public Driver(String driver_id, Location location) {
        this.driver_id = driver_id;
        this.location = location;


    }

    public String getDriver_id() {
        return driver_id;
    }

    public Location getLocation() {
        return location;
    }


}
    
    
