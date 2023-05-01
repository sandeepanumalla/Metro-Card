package com.geektrust.backend.Commands;

import com.geektrust.backend.commands.PrintSummaryCommand;
import com.geektrust.backend.service.IConsolePrinterService;
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
    IConsolePrinterService consolePrinterService;

    @DisplayName("check if printSummary command calls the printSummary()")
    @Test
    void testPrintSummaryCommandTest() throws Exception {
        List<String> tokens = new ArrayList<>(){{}};

        PrintSummaryCommand printSummaryCommand = new PrintSummaryCommand(consolePrinterService);
        doNothing().when(consolePrinterService).printSummary();
        printSummaryCommand.execute(tokens);
        verify(consolePrinterService, times(1)).printSummary();
    }
}
