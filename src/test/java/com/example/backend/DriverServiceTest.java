package com.example.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DriverServiceTest {

    private DriverService driverService;

    @BeforeEach
    public void setUp() {
        driverService = new DriverService();
    }

    @Test
    public void testAddDriver() {
        // Arrange
        String driverId = "D001";
        Location location = new Location(10.0, 20.0);

        // Act
        driverService.addDriver(driverId, location);

        // Assert
        List<Driver> driverList = driverService.getAvailableDriverList();
        Assertions.assertEquals(1, driverList.size());
        Assertions.assertEquals(driverId, driverList.get(0).getDriver_id());
        Assertions.assertEquals(location, driverList.get(0).getLocation());
    }

    @Test
    public void testGetAvailableDriverList() {
        // Arrange
        String driverId1 = "D001";
        Location location1 = new Location(10.0, 20.0);
        driverService.addDriver(driverId1, location1);

        String driverId2 = "D002";
        Location location2 = new Location(30.0, 40.0);
        driverService.addDriver(driverId2, location2);

        // Act
        List<Driver> driverList = driverService.getAvailableDriverList();

        // Assert
        Assertions.assertEquals(2, driverList.size());
        Assertions.assertEquals(driverId1, driverList.get(0).getDriver_id());
        Assertions.assertEquals(location1, driverList.get(0).getLocation());
        Assertions.assertEquals(driverId2, driverList.get(1).getDriver_id());
        Assertions.assertEquals(location2, driverList.get(1).getLocation());
    }

    @Test
    public void testAddMatchedDriverId() {
        // Arrange
        String driverId = "D001";

        // Act
        driverService.addMatchedDriverId(driverId);

        // Assert
        Assertions.assertTrue(driverService.getDriverAvailability(driverId));
    }

    @Test
    public void testRemoveMatchedDriverId() {
        // Arrange
        String driverId = "D001";
        driverService.addMatchedDriverId(driverId);

        // Act
        driverService.removeMatchedDriverId(driverId);

        // Assert
        Assertions.assertFalse(driverService.getDriverAvailability(driverId));
    }

    @Test
    public void testGetDriverAvailability() {
        //Arrange
        String driverId = "D001";
        driverService.addMatchedDriverId(driverId);

        // Act
        boolean actualResult = driverService.getDriverAvailability(driverId);
        Assertions.assertEquals(true, actualResult);

    }

    @Test
    public void testGetDriverAvailabilityWhenDriverIsUnavailable() {
        //Arrange
        String driverId = "D001";

        // Act
        boolean actualResult = driverService.getDriverAvailability(driverId);
        //Assert
        Assertions.assertEquals(false, actualResult);

    }
}