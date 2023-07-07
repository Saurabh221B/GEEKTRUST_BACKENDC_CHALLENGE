package com.example.backend;

import java.text.DecimalFormat;

public class   Payment {
    private final double BASE_FARE = 50;
    private final double ADDITIONAL_CHARGES_FOR_EVERY_KM = 6.5;
    private final double TIME_SPENT_PER_MIN_IN_RIDE = 2;
    private final double SERVICE_TAX = 0.20;

    private final MatcherService matcherService;
    private static DecimalFormat df = new DecimalFormat("#.##");


    public Payment(MatcherService matcherService) {
        this.matcherService = matcherService;
    }


    public void printTotalBill(String rideId) {

        Ride ride = matcherService.getRideById(rideId);
        if(ride==null){
            System.out.println("INVALID_RIDE");
            return;
        }
        Driver driver = ride.getDriver();
        double timeTakenInMin = ride.getTimeTakenInMin();
        double totalDistanceTraveled = ride.getTotalDistanceTraveled();

        double totalCost = calculateTotalCost(totalDistanceTraveled, timeTakenInMin);
        totalCost += calculateServiceTax(totalCost);

        String formattedTotalCost = String.format("%.2f", totalCost);

        System.out.println("BILL " + rideId + " " + driver.getDriver_id() + " " + formattedTotalCost);
    }

    private double calculateTotalCost(double totalDistanceTraveled, double timeTakenInMin) {
        //  System.out.println(totalDistanceTraveled);
        return BASE_FARE + (totalDistanceTraveled * ADDITIONAL_CHARGES_FOR_EVERY_KM) +
                (TIME_SPENT_PER_MIN_IN_RIDE * timeTakenInMin);
    }

    private double calculateServiceTax(double totalCost) {
        //return totalCost * SERVICE_TAX;
        return Double.parseDouble(df.format(totalCost * SERVICE_TAX));

    }
}
    