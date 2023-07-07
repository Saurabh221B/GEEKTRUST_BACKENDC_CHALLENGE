package com.example.backend;

import java.util.*;

public class MatcherService {

    private final DriverService driverService;
    private final RiderService riderService;
    private final Map<String, Ride>  rideList;
    private final DistanceCalculator distanceCalculator;

    public MatcherService( DriverService driverService,RiderService riderService) {
        this.driverService = driverService;
        this.riderService = riderService;
        this.distanceCalculator=new DistanceCalculator();
        rideList = new HashMap<>();
    }

    public boolean isRideAvailable(String rideId){
        return rideList.containsKey(rideId);

    }

    public void match(String riderId) {
        Rider rider = riderService.getRiderById(riderId);
        List<Driver> nearbyDrivers = findNearbyDrivers(rider);
        rider.addNearbyDrivers(nearbyDrivers);

        if (nearbyDrivers.isEmpty()) {
            System.out.println("NO_DRIVERS_AVAILABLE");
        } else {
            printMatchedDrivers(nearbyDrivers);
        }
    }

    private List<Driver> findNearbyDrivers(Rider rider) {
        List<Driver> availableDriverList = driverService.getAvailableDriverList();
        List<Driver> nearbyDrivers = filterDriversByDistance(rider, availableDriverList);
        sortDriversByDistanceAndId(rider, nearbyDrivers);
        return nearbyDrivers;
    }

    private List<Driver> filterDriversByDistance(Rider rider, List<Driver> drivers) {
        List<Driver> nearbyDrivers = new ArrayList<>();
        double MAX_DISTANCE_IN_KM = 5.0;

        for (Driver driver : drivers) {
            if (isDriverNearby(rider, driver, MAX_DISTANCE_IN_KM)) {
                nearbyDrivers.add(driver);
            }
        }

        return nearbyDrivers;
    }

    private boolean isDriverNearby(Rider rider, Driver driver, double maxDistance) {
        return distanceCalculator.calculateDistance(rider.getLocation(), driver.getLocation()) <= maxDistance;
    }

    private void sortDriversByDistanceAndId(Rider rider, List<Driver> drivers) {
        drivers.sort(Comparator.comparingDouble(driver -> calculateDistanceWithRider(rider, (Driver) driver))
                .thenComparing(driver -> ((Driver) driver).getDriver_id()));
    }

    private double calculateDistanceWithRider(Rider rider, Driver driver) {
        return distanceCalculator.calculateDistance(rider.getLocation(), driver.getLocation());
    }

    private void printMatchedDrivers(List<Driver> nearbyDrivers) {
        System.out.print("DRIVERS_MATCHED ");
        int MAX_NEAREST_DRIVERS = 5;
        nearbyDrivers.stream()
                .map(Driver::getDriver_id)
                .limit(MAX_NEAREST_DRIVERS)
                .forEach(driverId -> System.out.print(driverId + " "));
        System.out.println();
    }

    public void startRide(String rideId, int driverNo, String riderId) {
        Rider rider = riderService.getRiderById(riderId);
        List<Driver> matchedDriversList = rider.getNearbyDrivers();

        if (matchedDriversList.size() < driverNo || isRideAvailable(rideId) ) {
            System.out.println("INVALID_RIDE");
            return;
        }
        driverNo=driverNo-1;
        Driver matchedDriver = matchedDriversList.get(driverNo);

        if (driverService.getDriverAvailability(matchedDriver.getDriver_id())) {
            System.out.println("INVALID_RIDEE");
        } else {
            Ride ride = createRide(rideId, rider, matchedDriver);
            addRidetoList(rideId, ride);
            System.out.println("RIDE_STARTED" + " " + rideId);
        }
    }

    private Ride createRide(String rideId, Rider rider, Driver driver) {
        driverService.addMatchedDriverId(driver.getDriver_id());
        //driver.changeDriverStatus(true);
        Ride ride = new Ride(rideId);
        ride.setRider(rider);
        ride.setDriver(driver);
        ride.setRideStatus(true);
        return ride;
    }

    public void stopRide(String rideId, double destinationX, double destinationY, int timeTakenInMin) {
        if(!isRideAvailable(rideId)){
            System.out.println("INVALID_RIDE");
            return;
        }
        Ride ride = rideList.get(rideId);
        Location destination = new Location(destinationX, destinationY);
        if (!ride.getRideStatus()) {
            System.out.println("INVALID_RIDE");
        } else {
            Rider rider = ride.getRider();
            ride.setRideStatus(false);
            Driver driver=ride.getDriver();
            String driverId=driver.getDriver_id();
            driverService.removeMatchedDriverId(driverId);
            double totalDistanceTravelled =distanceCalculator.calculateDistance(rider.getLocation(), destination);
            ride.setTotalDistanceTraveled(totalDistanceTravelled);
            ride.setTimeTakenInMin(timeTakenInMin);
            System.out.println("RIDE_STOPPED" + " " + rideId);
        }
    }

    public Map<String,Ride> getRideList(){
        return rideList;
    }
    public void addRidetoList(String rideId, Ride ride){
        rideList.put(rideId, ride);
    }
    public Ride getRideById(String rideId){
        return rideList.get(rideId);
    }
}