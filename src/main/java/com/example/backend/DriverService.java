package com.example.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DriverService {
    private final List<Driver> driverList;
    private final HashSet<String> matchedDriverList;

    public DriverService() {
        driverList = new ArrayList<>();
        matchedDriverList=new HashSet<>();
    }

    public void addDriver(String driverId, Location location) {
        Driver driver = new Driver(driverId, location);
        driverList.add(driver);
    }

    public List<Driver> getAvailableDriverList() {
        List<Driver> availableDriverList = new ArrayList<>();
        for (Driver driver : driverList) {
            if (!getDriverAvailability(driver.getDriver_id())) {
                availableDriverList.add(driver);
            }
        }
        return availableDriverList;
    }
    public void addMatchedDriverId(String drive_id){
        matchedDriverList.add(drive_id);
    }

    public void removeMatchedDriverId(String drive_id){
        matchedDriverList.remove(drive_id);
    }
    public boolean getDriverAvailability(String drive_id){
        return matchedDriverList.contains(drive_id);

    }

}

