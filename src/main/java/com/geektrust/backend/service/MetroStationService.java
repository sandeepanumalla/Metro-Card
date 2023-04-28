package com.geektrust.backend.service;

import java.util.*;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.exceptions.InsufficientBalanceException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.repositories.IMetroStationRepository;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;

public class MetroStationService implements IMetroStationService<MetroStation>{
    private final IMetroStationRepository<MetroStation, String> metroStationRepository;
    private final IMetroCardService<MetroCard> metroCardService;

    private final IPassengerJourneyRepository passengerJourneyRepository;
    private MetroCard metroCard;
    
    public MetroStationService(IMetroStationRepository<MetroStation, String> metroStationRepository,
                               IMetroCardService<MetroCard> metroCardService,
                               IPassengerJourneyRepository passengerJourneyRepository
    ){
        this.metroStationRepository = metroStationRepository;
        this.metroCardService = metroCardService;
        this.passengerJourneyRepository = passengerJourneyRepository;
}

    @Override
    public MetroStation getMetroStation(String metroStation) {
        return metroStationRepository.find(metroStation).orElseThrow(()->
                new NoSuchElementException(metroStation + " metro station not found"));
    }
    

    @Override
    public void doCheckInProgress(String passengerCard, String passengerType, String originStation) throws InsufficientBalanceException {
        this.metroCard = metroCardService
                                        .getMetroCard(passengerCard)
                                        .orElseThrow(() -> 
                                        new MetroCardNotFoundException("Metro Card not found"));

        this.metroCard.setPassengerType(passengerType);
        boolean isReturnJourney = isReturnJourney(this.metroCard);
        metroCardService.processJourney(passengerCard, originStation, isReturnJourney);
        getMetroStation(originStation).updatePassengersTravelledSummary(this.metroCard);
    }

    @Override
    public boolean isReturnJourney(MetroCard metroCard) {
        boolean isReturnJourney = passengerJourneyRepository.getPassengerTravelHistory().containsKey(metroCard);
        passengerJourneyRepository.updatePassengerTravelHistory(metroCard);
        return isReturnJourney;
    }

    public void printSummary() {
        List<MetroStation> listOfStations =  metroStationRepository.findAll();
        for (MetroStation station: listOfStations) {
            printTotalCollection(station);
            printPassengerTypeSummary(station);
        }
    }

    private void printTotalCollection(MetroStation station) {
        System.out.print("TOTAL_COLLECTION ");
        System.out.print(station.getName() + " ");
        System.out.print(station.getCollections() + " ");
        System.out.print(station.getTotalDiscounts() + "\n");
    }

    private void printPassengerTypeSummary(MetroStation station) {
        System.out.println("PASSENGER_TYPE_SUMMARY");
        Map<PassengerType, Integer> PassengerTypesMap = createPassengerTypesMap(station.getPassengersTravelledSummary());

        List<Map.Entry<PassengerType, Integer>>  entryList = new ArrayList<>(PassengerTypesMap.entrySet());

            entryList.sort((a, b) ->{
                int condition = b.getValue().compareTo(a.getValue());
                if(condition == 0){
                    return a.getKey().compareTo(b.getKey());
                }
                return condition;
            });
            entryList.forEach(passenger -> System.out.println(passenger.getKey() + " " + passenger.getValue()));
            PassengerTypesMap.clear();
            entryList.clear();
    }

    private Map<PassengerType, Integer> createPassengerTypesMap(Map<MetroCard, Integer> passengersTravelledSummary) {
        Map<PassengerType, Integer> passengerTypesMap = new HashMap<>();

        for (Map.Entry<MetroCard, Integer> entry : passengersTravelledSummary.entrySet()) {
            PassengerType passengerType = entry.getKey().getPassengerType();
            passengerTypesMap.compute(passengerType, (k, v) -> (v == null) ? entry.getValue() : v + entry.getValue());
        }

        return passengerTypesMap;
    }
}