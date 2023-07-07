package com.example.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.mockito.Mockito.*;
//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CommandProcessorTest {
    @Mock
    private DriverService mockDriverService;

    @Mock
    private RiderService mockRiderService;

    @Mock
    private MatcherService mockMatcherService;

    @Mock
    private Payment mockPayment;
    @InjectMocks
    private CommandProcessor commandProcessor;

    @BeforeEach
    public void setUp() {
        //MockitoAnnotations.openMocks(this);
        commandProcessor = new CommandProcessor(mockDriverService, mockRiderService, mockMatcherService, mockPayment);
    }

    @Test
    public void testProcessAddDriverCommand() {
        String[] command = {"ADD_DRIVER", "driverId", "1", "2"};
        Location location=new Location(1,2);

        commandProcessor.processAddDriverCommand(command);

        verify(mockDriverService).addDriver("driverId", location);
    }

    @Test
    public void testProcessAddRiderCommand() {
        String[] command = {"ADD_RIDER", "riderId", "3", "4"};
        Location expectedLocation = new Location(3, 4);

        commandProcessor.processAddRiderCommand(command);

        verify(mockRiderService).addRider(eq("riderId"), eq(expectedLocation));
    }

}