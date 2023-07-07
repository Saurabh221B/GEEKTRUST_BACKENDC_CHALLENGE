package com.example.backend;

public class Ride {
    private String ride_id;
    private Rider rider;
    private Driver driver;
    private boolean isRideInProgress;
    private double timeTakenInMin;
    private double totalDistanceTraveled;

    public Ride(String ride_id) {
        this.ride_id = ride_id;
        isRideInProgress = true;
    }

    public String getRide_id() {
        return ride_id;
    }


    // Getter and Setter for rider
    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    // Getter and Setter for driver
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    // Getter and Setter for timeTakeninMin
    public double getTimeTakenInMin() {
        return timeTakenInMin;
    }

    public void setTimeTakenInMin(double timeTakenInMin) {
        this.timeTakenInMin = timeTakenInMin;
    }

    // Getter and Setter for totalDistanceTraveled
    public double getTotalDistanceTraveled() {
        return totalDistanceTraveled;
    }

    public void setTotalDistanceTraveled(double totalDistanceTraveled) {
        this.totalDistanceTraveled = totalDistanceTraveled;
    }

    public boolean getRideStatus() {
        return isRideInProgress;
    }

    // Getter and Setter for totalDistanceTraveled
    public void setRideStatus(boolean isRideInProgress) {
        this.isRideInProgress = isRideInProgress;
    }


}
