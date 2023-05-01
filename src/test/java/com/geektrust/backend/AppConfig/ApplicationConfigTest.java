package com.geektrust.backend.AppConfig;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.geektrust.backend.commands.*;
import com.geektrust.backend.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.repositories.IMetroStationRepository;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;

import java.util.List;

class ApplicationConfigTest {

    private ApplicationConfig appConfig;

    @BeforeEach
    void setUp() throws Exception {
        appConfig = new ApplicationConfig();
    }

    @Test
    void testLoadPassengerFares() {
        // Verify that passenger fares are loaded correctly
        appConfig.loadPassengerFares();
        IPassengerJourneyRepository passengerJourneyRepository = appConfig.passengerJourneyRepository;
        assertNotNull(passengerJourneyRepository);
        assertNotNull(passengerJourneyRepository);
    }

    @Test
    void testLoadMetroStationsData() {
        // Verify that metro stations data is loaded correctly
        appConfig.loadMetroStationsData();
        IMetroStationRepository<MetroStation, String> metroStationRepository = appConfig.metroStationRepository;
        assertNotNull(metroStationRepository.find("Purushottam Park"));
        assertNotNull(metroStationRepository.find("Akurdi"));
    }



    @Test
    void testGetCommandInvoker() {
        // Verify that all the commands are registered in the command invoker
        CommandInvoker commandInvoker = appConfig.getCommandInvoker();
        ICommand balanceCommand = commandInvoker.get(Command.BALANCE);
        ICommand checkInCommand = commandInvoker.get(Command.CHECK_IN);
        ICommand printSummaryCommand = commandInvoker.get(Command.PRINT_SUMMARY);
        assertTrue(balanceCommand instanceof BalanceCommand);
        assertTrue(checkInCommand instanceof CheckInCommand);
        assertTrue(printSummaryCommand instanceof PrintSummaryCommand);
    }

    @Test
    public void testCreatePrintSummaryCommand() {
        IConsolePrinterService consolePrinterService = new ConsolePrinterService(null, null);
        ICommand printSummaryCommand = new PrintSummaryCommand(consolePrinterService);
        assertNotNull(printSummaryCommand);
    }

    @Test
    public void testCommandInvoker() {
        CommandInvoker commandInvoker = appConfig.getCommandInvoker();
        assertNotNull(commandInvoker);

        ICommand balanceCommand = commandInvoker.get(Command.BALANCE);
        assertNotNull(balanceCommand);
        assertTrue(balanceCommand instanceof BalanceCommand);

        ICommand checkInCommand = commandInvoker.get(Command.CHECK_IN);
        assertNotNull(checkInCommand);
        assertTrue(checkInCommand instanceof CheckInCommand);

        ICommand printSummaryCommand = commandInvoker.get(Command.PRINT_SUMMARY);
        assertNotNull(printSummaryCommand);
        assertTrue(printSummaryCommand instanceof PrintSummaryCommand);
    }

    @Test
    public void testPrintSummaryCommandExecute() throws Exception {
        IConsolePrinterService consolePrinterService = mock(ConsolePrinterService.class);
        ICommand printSummaryCommand = new PrintSummaryCommand(consolePrinterService);
         printSummaryCommand.execute(List.of(""));
        verify(consolePrinterService).printSummary();
    }
}