package com.geektrust.backend.service;

import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.repositories.IMetroStationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsolePrinterService implements IConsolePrinterService{

    final  IMetroStationRepository<MetroStation, String> metroStationRepository;
    final IPassengerJourneyService passengerJourneyService;
    public ConsolePrinterService(IMetroStationRepository<MetroStation, String> metroStationRepository, IPassengerJourneyService passengerJourneyService) {
        this.metroStationRepository = metroStationRepository;
        this.passengerJourneyService = passengerJourneyService;
    }
    public void printSummary() {
        List<MetroStation> listOfStations =  metroStationRepository.findAll();
        for (MetroStation station: listOfStations) {
            printTotalCollection(station);
            printPassengerTypeSummary(station);
        }
    }

    public void printTotalCollection(MetroStation station) {
        System.out.print("TOTAL_COLLECTION ");
        System.out.print(station.getName() + " ");
        System.out.print(station.getCollections() + " ");
        System.out.print(station.getTotalDiscounts() + "\n");
    }

    public void printPassengerTypeSummary(MetroStation station) {
        System.out.println("PASSENGER_TYPE_SUMMARY");
        Map<PassengerType, Integer> PassengerTypesMap = passengerJourneyService
                            .createPassengerTypesMap(station.getPassengersTravelledSummary());

        List<Map.Entry<PassengerType, Integer>> entryList = new ArrayList<>(PassengerTypesMap.entrySet());
        sortPassengers(entryList);
        entryList.forEach(passenger -> System.out.println(passenger.getKey() + " " + passenger.getValue()));
        PassengerTypesMap.clear();
        entryList.clear();
    }
    public void sortPassengers(List<Map.Entry<PassengerType, Integer>> entryList) {
        entryList.sort((a, b) -> {
            int condition = b.getValue().compareTo(a.getValue());
            if (condition == 0) {
                return a.getKey().name().compareTo(b.getKey().name());
            }
            return condition;
        });
    }
}
