package com.example.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DistanceCalculatorTest {
    DistanceCalculator distanceCalculator=new DistanceCalculator();


    @Test
    public void testDistanceCalculator(){
        //Arrange
        Location riderLocation=new Location(5.0,6.0);
        Location driverLocation=new Location(3.5,3.6);
        //Act
        double actualResult=distanceCalculator.calculateDistance(riderLocation,driverLocation);
        //Assert
        Assertions.assertEquals(2.83,actualResult);
    }


}
