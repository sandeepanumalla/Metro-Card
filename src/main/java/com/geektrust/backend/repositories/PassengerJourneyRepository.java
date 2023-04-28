package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PassengerJourneyRepository implements IPassengerJourneyRepository{

    private Map<MetroCard, Integer> passengerTravelHistory = new HashMap<>();
    private final Map<PassengerType, Integer> fareForPassengerType = new HashMap<PassengerType, Integer>(){
        {
            put(PassengerType.ADULT, 200);
            put(PassengerType.SENIOR_CITIZEN, 100);
            put(PassengerType.KID, 50);
        }
    };

    public void setPassengersReturnJourney(Map<MetroCard, Integer> passengersTravelled) {
        this.passengerTravelHistory = passengersTravelled;
    }

    public long getFareByPassengerType(PassengerType passenger) {
        return this.fareForPassengerType.get(passenger);
    }
    public void updatePassengerTravelHistory( MetroCard metroCard) {
        if(this.getPassengerTravelHistory().containsKey(metroCard)){
            this.getPassengerTravelHistory().remove(metroCard);
        }
        else{
            this.getPassengerTravelHistory().putIfAbsent(metroCard, 1);
        }
    }

    @Override
    public void setFareByPassengerType(int fare, PassengerType passengerType) throws IllegalArgumentException {
        if(fare < 0) {
            throw new IllegalArgumentException("Negative values are not allowed to set fares for passenger type");
        }
        this.fareForPassengerType.putIfAbsent(passengerType, fare);
    }

    public Map<MetroCard, Integer> getPassengerTravelHistory() {
        return passengerTravelHistory;
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
