package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroPassenger;
import com.geektrust.backend.entities.MetroStation;

public class MetroStationRepository implements IMetroStationRepository<MetroStation, String>{

    private List<MetroStation> listOfMetroStations = new ArrayList<MetroStation>(){
        {
            add(new MetroStation("1", "CENTRAL", 0, 0));
            add(new MetroStation("2", "AIRPORT", 0, 0));
        }
    };
    private Map<MetroCard, Integer> passengersReturnJourney = new HashMap<>();
    private Map<String, Integer> amountRequiredForPassengerType = new HashMap<String, Integer>(){
        {
            put("ADULT", 200);
            put("SENIOR_CITIZEN", 100);
            put("KID", 50);
        }
    };
    
    public void MetroCardRepository(){

    }
    public void MetroCardRepository(MetroStation entity){
        listOfMetroStations.add(entity);
    }
    @Override
    public MetroStation save(MetroStation entity) {
        if(!existsById(entity)){
            listOfMetroStations.add(entity);
        }
        return entity;
    }

    public void setPassengersReturnJourney(Map<MetroCard, Integer> passengersTravelled) {
        this.passengersReturnJourney = passengersTravelled;
    }

    public Map<String, Integer> getAmountRequiredForPassengerType() {
        return amountRequiredForPassengerType;
    }

    public long getAmountRequiredForPassengerType(MetroPassenger passenger) {
        return this.amountRequiredForPassengerType.get(passenger.toString());
    }

    public long getAmountRequiredForPassengerType(String passengerCard) {
        return this.amountRequiredForPassengerType.get(passengerCard);
    }

    public Map<MetroCard, Integer> getPassengersReturnJourney() {
        return passengersReturnJourney;
    }
 
    public void updatePassengerInReturnList( MetroCard metroCard) {
        if(this.getPassengersReturnJourney().containsKey(metroCard)){
            this.getPassengersReturnJourney().remove(metroCard);
        }
        else{
            this.getPassengersReturnJourney().computeIfAbsent(metroCard, (K) -> 1);
        }
    }

    @Override
    public List<MetroStation> findAll() {
        return listOfMetroStations;
    }

    @Override
    public Optional<MetroStation> findById(String id) {
        return listOfMetroStations.stream().filter(each -> each.getId().equals(id))
                                           .findAny();
    }

    @Override
    public boolean existsById(MetroStation entity) {
        return listOfMetroStations.stream().filter(e -> e.getId().equals(entity.getId()))
                                            .findAny()
                                            .isPresent();
    }

    @Override
    public int countStations() {
        return listOfMetroStations.size();
    }

    @Override
    public Optional<MetroStation> find(String name) {
        return listOfMetroStations.stream().filter(str -> str.getName().equals(name))
                                            .findAny();
    }

}
