package com.example.backend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatcherServiceTest {


    @Mock
    private DriverService driverServiceMock;

    @Mock
    private RiderService riderServiceMock;
    @InjectMocks
    private MatcherService matcherService;

    ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        outputStream=new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        //MockitoAnnotations.openMocks(this);
        //matcherService = new MatcherService(driverService, riderService);
    }

    @Test
    public void testMatch_NoDriversAvailable() {
        // Arrange
        String riderId = "R001";
        Location location = new Location(10.0, 20.0);
        Rider rider=new Rider(riderId,location);

        Mockito.when(riderServiceMock.getRiderById(riderId)).thenReturn(new Rider(riderId, location));
        Mockito.when(driverServiceMock.getAvailableDriverList()).thenReturn(new ArrayList<>());

        // Act
        matcherService.match(riderId);

        // Assert
        String actualResult=outputStream.toString().trim();
        Assertions.assertEquals("NO_DRIVERS_AVAILABLE",actualResult);
        // ...
    }

    @Test
    public void testMatch_DriversAvailable() {
        // Arrange
        String riderId = "R001";
        Location location = new Location(10.0, 20.0);
        Mockito.when(riderServiceMock.getRiderById(riderId)).thenReturn(new Rider(riderId, location));

        List<Driver> nearbyDrivers = new ArrayList<>();
        nearbyDrivers.add(new Driver("D001", new Location(11.0, 21.0)));
        nearbyDrivers.add(new Driver("D002", new Location(12.0, 22.0)));
        Mockito.when(driverServiceMock.getAvailableDriverList()).thenReturn(nearbyDrivers);

        // Act
        matcherService.match(riderId);

        // Assert
        String actualResult=outputStream.toString().trim();
        Assertions.assertEquals("DRIVERS_MATCHED D001 D002",actualResult);


        // ...
    }

    @Test
    public void testStartRide_WhenInValidInput() {
        // Mock data
        String rideId = "ride1";
        int driverNo = 3;
        String riderId = "rider1";
        Location riderLocation=mock(Location.class);
        Rider rider = new Rider(riderId,riderLocation);
        when(riderServiceMock.getRiderById(riderId)).thenReturn(rider);

        //Act
        matcherService.startRide(rideId,driverNo,riderId);
        //Assert
        String actualResult=outputStream.toString().trim();
        Assertions.assertEquals("INVALID_RIDE",actualResult);

    }
    @Test
    public void testStartRide_WhenInValidInput1() {
        // Mock data
        String rideId = "ride1";
        int driverNo = 2;
        String riderId = "rider1";
        Rider rider = mock(Rider.class);
        Driver matchedDriverMock = mock(Driver.class);

        List<Driver>matchedDriversList=mock(List.class);
        when(riderServiceMock.getRiderById(riderId)).thenReturn(rider);
        when(rider.getNearbyDrivers()).thenReturn(matchedDriversList);
        when(matchedDriversList.size()).thenReturn(4);
        when(matchedDriversList.get(anyInt())).thenReturn(matchedDriverMock);
        when(matchedDriverMock.getDriver_id()).thenReturn("driver");

        when(driverServiceMock.getDriverAvailability(anyString())).thenReturn(true);


        //Act
        matcherService.startRide(rideId,driverNo,riderId);
        //Assert
        String actualResult=outputStream.toString().trim();
        Assertions.assertEquals("INVALID_RIDEE",actualResult);

    }

    @Test
    public void testStartRide_WhenValidInput1() {
        // Mock data
        String rideId = "ride1";
        int driverNo = 2;
        String riderId = "rider1";
        Rider rider = mock(Rider.class);
        Driver matchedDriverMock = mock(Driver.class);

        List<Driver>matchedDriversList=mock(List.class);
        when(riderServiceMock.getRiderById(riderId)).thenReturn(rider);
        when(rider.getNearbyDrivers()).thenReturn(matchedDriversList);
        when(matchedDriversList.size()).thenReturn(4);
        when(matchedDriversList.get(anyInt())).thenReturn(matchedDriverMock);
        when(matchedDriverMock.getDriver_id()).thenReturn("driver");

        when(driverServiceMock.getDriverAvailability(anyString())).thenReturn(false);


        //Act
        matcherService.startRide(rideId,driverNo,riderId);
        //Assert
        String actualResult=outputStream.toString().trim();
        Assertions.assertEquals("RIDE_STARTED ride1",actualResult);

    }

    @Test
    public void testIsRideAvailable() {
        // Arrange
        String rideId = "123";
        MatcherService matcherServiceMock = mock(MatcherService.class);
        when(matcherServiceMock.isRideAvailable(rideId)).thenReturn(false);

        // Act
        boolean isAvailable = matcherServiceMock.isRideAvailable(rideId);

        // Assert
        Assertions.assertFalse(isAvailable);
    }

    @Test
    public void testStopRide_whenRideNotStarted(){

       // when(matcherService.isRideAvailable("ride-1")).thenReturn(false);
        matcherService.stopRide("ride-1",0.0,0.0,5);

        String actualResult=outputStream.toString().trim();
        Assertions.assertEquals("INVALID_RIDE",actualResult);

    }

    @Test
    public void testStopide_WhenRideStatusIsInvalid(){
        String rideId="ride-1";
        Ride ride=new Ride(rideId);
        ride.setRideStatus(false);
        matcherService.addRidetoList(rideId,ride);

        // when(matcherService.isRideAvailable("ride-1")).thenReturn(false);
        matcherService.stopRide("ride-1",0.0,0.0,5);

        String actualResult=outputStream.toString().trim();
        Assertions.assertEquals("INVALID_RIDE",actualResult);

    }

    @Test
    public void testStopRide_whenValidInput(){
        //Arrange
        double destinationX=1.0;
        double destinationY=2.0;
        String rideId="ride-1";
        Ride ride=new Ride(rideId);
        ride.setRideStatus(true);
        DistanceCalculator distanceCalculatorMock=mock(DistanceCalculator.class);
        Location riderLocation=new Location(2,2);
        Rider rider=new Rider("rider1",riderLocation);
        Driver driver=new Driver("driver1",new Location(0,0));
        ride.setDriver(driver);
        ride.setRider(rider);

        doNothing().when(driverServiceMock).removeMatchedDriverId(anyString());
        //when(distanceCalculatorMock.calculateDistance(ride.getRider().getLocation(),new Location(destinationX,destinationY))).thenReturn(3.0);

        matcherService.addRidetoList(rideId,ride);
        //Act
        matcherService.stopRide("ride-1",destinationX,destinationY,5);

        //Assert
        Assertions.assertEquals(false,ride.getRideStatus());

    }


        @AfterEach
        public void tearDown () {
            System.setOut(System.out); // Reset System.out to normal
        }

        // Other test methods using Mockito for mocking dependencies

        // ...
    }
