package com.geektrust.backend.AppConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geektrust.backend.commands.*;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.repositories.*;
import com.geektrust.backend.service.IMetroCardService;
import com.geektrust.backend.service.IMetroStationService;
import com.geektrust.backend.service.MetroCardService;
import com.geektrust.backend.service.MetroStationService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ApplicationConfig {

    public ApplicationConfig() {
        this.loadMetroStationsData();
        this.loadPassengerFares();
    }
    IMetroCardRepository<MetroCard, String> metroCardRepository = new MetroCardRepository();
    IPassengerJourneyRepository passengerJourneyRepository = new PassengerJourneyRepository();
    IMetroStationRepository<MetroStation, String> metroStationRepository  = new MetroStationRepository();
    IMetroCardService<MetroCard> metroCardService = new MetroCardService(metroCardRepository, metroStationRepository, passengerJourneyRepository);
    IMetroStationService<MetroStation> metroStationService = new MetroStationService(metroStationRepository, metroCardService, passengerJourneyRepository);
    ICommand createBalanceCommand = new BalanceCommand(metroCardService);
    ICommand createCheckInCommand = new CheckInCommand(metroStationService);
    ICommand createPrintSummaryCommand = new PrintSummaryCommand(metroStationService);
    CommandInvoker commandInvoker = new CommandInvoker();

    public void loadPassengerFares() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/fares_for_passenger.json");
        HashMap<String, Integer> passengerFaresMap;
        try {
            passengerFaresMap = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Map.Entry<String, Integer> entry: passengerFaresMap.entrySet()) {
            PassengerType passengerType = PassengerType.valueOf(entry.getKey());
            passengerJourneyRepository.setFareByPassengerType(entry.getValue(), passengerType);
        }
    }

    public void loadMetroStationsData() {
        metroStationRepository.setUpMetroStations(new MetroStation("1", "CENTRAL", 0, 0));
        metroStationRepository.setUpMetroStations(new MetroStation("2", "AIRPORT", 0,0));
    }
    
    public CommandInvoker getCommandInvoker(){
        commandInvoker.register(Command.BALANCE, createBalanceCommand);
        commandInvoker.register(Command.CHECK_IN, createCheckInCommand);
        commandInvoker.register(Command.PRINT_SUMMARY, createPrintSummaryCommand);
        return commandInvoker;
    }
}
