package com.geektrust.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.repositories.MetroStationRepository;

public class MetroStationService implements IMetroStationService{
    private MetroStationRepository metroStationRepository = null;
    private IMetroCardService<MetroCard> metroCardService;
    private MetroCard metroCard;
    
    public MetroStationService(MetroStationRepository metroStationRepository,
    IMetroCardService<MetroCard> metroCardService
    ){
        this.metroStationRepository = metroStationRepository;
        this.metroCardService = metroCardService;
}

    @Override
    public MetroStation getMetroStation(String metroStation) {
        return metroStationRepository.find(metroStation).get();
    }
    

    @Override
    public void doCheckInProgress(String passengerCard, String passengerType, String arrivedFromStation) throws Exception{

        this.metroCard = (MetroCard) metroCardService
                                        .getMetroCard(passengerCard)
                                        .orElseThrow(() -> 
                                        new MetroCardNotFoundException("Metro Card not found"));

        this.metroCard.setPassengerType(passengerType);
        boolean isReturnJourney = isReturnJourney(this.metroCard);
        metroCardService.walletCheckService(passengerCard, arrivedFromStation, isReturnJourney);
        getMetroStation(arrivedFromStation).updatePassengersTravelledSummary(this.metroCard);
    }

    @Override
    public boolean isReturnJourney(MetroCard metroCard) {
        boolean isReturnJourney = false;
        if(metroStationRepository.getPassengersReturnJourney().containsKey(metroCard)){
            isReturnJourney = true;
        }
        metroStationRepository.updatePassengerInReturnList(metroCard);
        return isReturnJourney;
    }


    @Override
    public void printSummary() {
        List<MetroStation> listOfStations =  metroStationRepository.findAll();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>();
        Map<String, Integer> metroPassengersMap = new HashMap<>();
        listOfStations.forEach(each ->{
            System.out.print("TOTAL_COLLECTION ");
            System.out.print(each.getName()+" ");
            System.out.print(each.getCollections()+" ");
            System.out.print(each.getTotalDiscounts()+"\n");
            System.out.println("PASSENGER_TYPE_SUMMARY");
            each.getPassengersTravelledSummary().entrySet().stream().forEach(card ->{
                metroPassengersMap.computeIfPresent(card.getKey().getPassengerType().toString(), (K, V) -> V += 1);
                metroPassengersMap.computeIfAbsent(card.getKey().getPassengerType().toString(), (K) -> card.getValue());
            });

            entryList.addAll(metroPassengersMap.entrySet());
            entryList.sort((a, b) ->{
                int condition = b.getValue().compareTo(a.getValue());
                if(condition == 0){
                    return a.getKey().compareTo(b.getKey());
                }
                return condition;
            });
            entryList.forEach(passenger ->{
                System.out.println(passenger.getKey() + " " + passenger.getValue());
            });
            metroPassengersMap.clear();
            entryList.clear();
        });
    }
}