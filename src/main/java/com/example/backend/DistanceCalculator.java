package com.example.backend;

import java.text.DecimalFormat;

public class DistanceCalculator {


//    public  double calculateDistance(Location rider, Location driver) {
//        int power=2;
//        return Math.sqrt(Math.pow((driver.getxPosition() - rider.getxPosition()), power) + Math.pow((driver.getyPosition() - rider.getyPosition()), power));
//    }
private static DecimalFormat df = new DecimalFormat("##.##");

    public  double calculateDistance(Location rider, Location driver) {
        int power=2;

        double distance=Double.parseDouble(df.format(Math.sqrt(Math.pow((driver.getxPosition() - rider.getxPosition()), power) + Math.pow((driver.getyPosition() - rider.getyPosition()), power))));

        return distance;

    }


}
