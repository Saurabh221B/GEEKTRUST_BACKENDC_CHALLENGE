package com.example.backend;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

    public class RiderServiceTest {

        private RiderService riderService;

        @BeforeEach
        public void setUp() {
            riderService = new RiderService();
        }

        @Test
        public void testAddRider() {
            // Arrange
            String riderId = "R001";
            Location location = new Location(10.0, 20.0);

            // Act
            riderService.addRider(riderId, location);

            // Assert
            Rider rider = riderService.getRiderById(riderId);
            Assertions.assertNotNull(rider);
            Assertions.assertEquals(riderId, rider.getRiderId());
            Assertions.assertEquals(location, rider.getLocation());
        }

        @Test
        public void testGetRiderById() {
            // Arrange
            String riderId1 = "R001";
            Location location1 = new Location(10.0, 20.0);
            riderService.addRider(riderId1, location1);

            String riderId2 = "R002";
            Location location2 = new Location(30.0, 40.0);
            riderService.addRider(riderId2, location2);

            // Act
            Rider rider1 = riderService.getRiderById(riderId1);
            Rider rider2 = riderService.getRiderById(riderId2);

            // Assert
            Assertions.assertNotNull(rider1);
            Assertions.assertEquals(riderId1, rider1.getRiderId());
            Assertions.assertEquals(location1, rider1.getLocation());

            Assertions.assertNotNull(rider2);
            Assertions.assertEquals(riderId2, rider2.getRiderId());
            Assertions.assertEquals(location2, rider2.getLocation());
        }
        @Test
        public void testGetRiderById_RiderNotAvailable() {
            // Arrange
            String riderId = "R001";

            // Act
            Rider rider = riderService.getRiderById(riderId);

            // Assert
            Assertions.assertNull(rider);
        }

    }


