package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PassengerJourneyRepository implements IPassengerJourneyRepository{

    private Map<MetroCard, Integer> passengersReturnJourney = new HashMap<>();
    private final Map<PassengerType, Integer> amountRequiredForPassengerType = new HashMap<PassengerType, Integer>(){
        {
            put(PassengerType.ADULT, 200);
            put(PassengerType.SENIOR_CITIZEN, 100);
            put(PassengerType.KID, 50);
        }
    };

    public void setPassengersReturnJourney(Map<MetroCard, Integer> passengersTravelled) {
        passengersReturnJourney = passengersTravelled;
    }

    public long getAmountRequiredForPassengerType(PassengerType passenger) {
        return this.amountRequiredForPassengerType.get(passenger);
    }
    public void updatePassengerInReturnList( MetroCard metroCard) {
        if(this.getPassengersReturnJourney().containsKey(metroCard)){
            this.getPassengersReturnJourney().remove(metroCard);
        }
        else{
            this.getPassengersReturnJourney().putIfAbsent(metroCard, 1);
        }
    }

    public Map<MetroCard, Integer> getPassengersReturnJourney() {
        return passengersReturnJourney;
    }
    @Override
    public void save(PassengerType entity) {

    }

    @Override
    public List<PassengerType> findAll() {
        return null;
    }

    @Override
    public Optional<PassengerType> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(PassengerType entity) {
        return false;
    }
}
