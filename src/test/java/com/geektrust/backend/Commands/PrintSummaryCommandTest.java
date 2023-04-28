package com.geektrust.backend.Commands;

import com.geektrust.backend.commands.CheckInCommand;
import com.geektrust.backend.commands.PrintSummaryCommand;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.service.IMetroStationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrintSummaryCommandTest {

    @Mock
    IMetroStationService<MetroStation> metroStationService;


    @DisplayName("check if printSummary command calls the printSummary()")
    @Test
    void testPrintSummaryCommandTest() throws Exception {
        List<String> tokens = new ArrayList<>(){{}};

        PrintSummaryCommand printSummaryCommand = new PrintSummaryCommand(metroStationService);
        doNothing().when(metroStationService).printSummary();
        printSummaryCommand.execute(tokens);
        verify(metroStationService, times(1)).printSummary();
    }
}
