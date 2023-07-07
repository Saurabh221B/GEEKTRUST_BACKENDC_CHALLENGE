package com.example.backend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class PaymentTest {

    @Mock
    private MatcherService matcherServiceMock;

    @InjectMocks
    private Payment payment;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        outputStream=new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

    }

    @Test
    public void testPrintTotalBill(){
        //Arrange
        String rideId="ride-1";
        Ride ride=new Ride(rideId);
        Location riderLocation=new Location(2,2);
        Rider rider=new Rider("rider1",riderLocation);
        Driver driver=new Driver("driver1",new Location(0,0));
        ride.setDriver(driver);
        ride.setRider(rider);
        ride.setTotalDistanceTraveled(5.0);
        ride.setTimeTakenInMin(15);
        when(matcherServiceMock.getRideById(anyString())).thenReturn(ride);

        //Act
        payment.printTotalBill(rideId);

        //Assert
        String actualResult=outputStream.toString().trim();
        Assertions.assertEquals("BILL ride-1 driver1 135.00",actualResult);


    }
    @Test
    public void printTotalBillWhenRideIsNull(){
        String rideId="ride-1";
        when(matcherServiceMock.getRideById(anyString())).thenReturn(null);
        //Act
        payment.printTotalBill(rideId);

        //Assert
        String actualResult=outputStream.toString().trim();
        Assertions.assertEquals("INVALID_RIDE",actualResult);

    }


    @AfterEach
    public void tearDown () {
        System.setOut(System.out); // Reset System.out to normal
    }


}
