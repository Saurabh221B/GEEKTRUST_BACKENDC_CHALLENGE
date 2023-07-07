package com.example.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CommandProcessor {
    private final DriverService driverService;
    private final RiderService riderService;
    private final MatcherService matcherService;
    private final Payment payment;

    public CommandProcessor(DriverService driverService, RiderService riderService, MatcherService matcherService, Payment payment) {
        this.driverService = driverService;
        this.riderService = riderService;
        this.matcherService = matcherService;
        this.payment = payment;
    }

    public void processCommandsFromFile(String filePath) {
        //File file=new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] command = line.split(" ");

                if (command.length > 0) {
                    switch (command[0]) {
                        case "ADD_DRIVER":
                            processAddDriverCommand(command);
                            break;

                        case "ADD_RIDER":
                            processAddRiderCommand(command);
                            break;

                        case "MATCH":
                            processMatchCommand(command);
                            break;

                        case "START_RIDE":
                            processStartRideCommand(command);
                            break;

                        case "STOP_RIDE":
                            processStopRideCommand(command);
                            break;

                        case "BILL":
                            processBillCommand(command);
                            break;

                        default:
                            System.out.println("Unknown command");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processAddDriverCommand(String[] command) {

        String driverId = command[1];
        int xCoordinate = Integer.parseInt(command[2]);
        int yCoordinate = Integer.parseInt(command[3]);
        Location driverLocation = new Location(xCoordinate, yCoordinate);
        driverService.addDriver(driverId, driverLocation);

    }

    public void processAddRiderCommand(String[] command) {

        String riderId = command[1];
        int xCoordinate = Integer.parseInt(command[2]);
        int yCoordinate = Integer.parseInt(command[3]);
        Location riderLocation = new Location(xCoordinate, yCoordinate);
        riderService.addRider(riderId, riderLocation);

    }

    private void processMatchCommand(String[] command) {

        String riderId = command[1];
        matcherService.match(riderId);

    }

    private void processStartRideCommand(String[] command) {

        String rideId = command[1];
        int n = Integer.parseInt(command[2]);
        String riderId = command[3];
        matcherService.startRide(rideId, n, riderId);

    }

    private void processStopRideCommand(String[] command) {

        String rideId = command[1];
        int destinationX = Integer.parseInt(command[2]);
        int destinationY = Integer.parseInt(command[3]);
        int timeTaken = Integer.parseInt(command[4]);
        matcherService.stopRide(rideId, destinationX, destinationY, timeTaken);

    }

    private void processBillCommand(String[] command) {

        String rideId = command[1];
        payment.printTotalBill(rideId);

    }
}
