package com.geektrust.backend.AppConfig;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.repositories.*;
import com.geektrust.backend.service.IMetroCardService;
import com.geektrust.backend.service.IMetroStationService;
import com.geektrust.backend.service.MetroCardService;
import com.geektrust.backend.service.MetroStationService;
import com.geektrust.backend.commands.BalanceCommand;
import com.geektrust.backend.commands.CheckInCommand;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.commands.ICommand;
import com.geektrust.backend.commands.PrintSummaryCommand;

public class ApplicationConfig {
    IMetroCardRepository<MetroCard, String> metroCardRepository = new MetroCardRepository();
    IPassengerJourneyRepository passengerJourneyRepository = new PassengerJourneyRepository();
    IMetroStationRepository<MetroStation, String> metroStationRepository  = new MetroStationRepository();
    IMetroCardService<MetroCard> metroCardService = new MetroCardService(metroCardRepository, metroStationRepository, passengerJourneyRepository);
    IMetroStationService<MetroStation> metroStationService = new MetroStationService(metroStationRepository, metroCardService, passengerJourneyRepository);
    ICommand createBalanceCommand = new BalanceCommand(metroCardService);
    ICommand createCheckInCommand = new CheckInCommand(metroStationService);
    ICommand createPrintSummaryCommand = new PrintSummaryCommand(metroStationService);

    CommandInvoker commandInvoker = new CommandInvoker();
    
    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("BALANCE", createBalanceCommand);
        commandInvoker.register("CHECK_IN", createCheckInCommand);
        commandInvoker.register("PRINT_SUMMARY", createPrintSummaryCommand);
        return commandInvoker;
    }
}
