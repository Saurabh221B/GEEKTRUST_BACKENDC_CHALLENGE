package com.example.backend;

public class App {

    public static void main(String[] args) {
        //System.out.println("Welcome to Geektrust Backend Challenge!");
        DriverService driverService = new DriverService();
        RiderService riderService = new RiderService();

        MatcherService matcherService = new MatcherService(driverService, riderService);
        Payment payment = new Payment(matcherService);


		//String filePath ="C:\\Users\\SAURABH\\Documents\\backend\\geektrust-challenge-gradle-java-template-main\\src\\main\\java\\com\\example\\backend\\input.txt";
        String filePath = args[0];
        CommandProcessor processor = new CommandProcessor(driverService, riderService, matcherService, payment);
        processor.processCommandsFromFile(filePath);
    }

}

