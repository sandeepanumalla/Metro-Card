package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;

import java.util.*;

public class PassengerJourneyRepository implements IPassengerJourneyRepository{

    private final Set<MetroCard> passengerTravelHistory = new HashSet<>();
    private final Map<PassengerType, Integer> fareForPassengerType = new HashMap<>();

    public long getFareByPassengerType(PassengerType passenger) {
        return this.fareForPassengerType.get(passenger);
    }
    public void updatePassengerTravelHistory( MetroCard metroCard) {
        if(getPassengerTravelHistory().contains(metroCard)) {
            getPassengerTravelHistory().remove(metroCard);
        } else {
            getPassengerTravelHistory().add(metroCard);
        }
    }

    @Override
    public void setFareByPassengerType(int fare, PassengerType passengerType) throws IllegalArgumentException {
        if(fare < 0) {
            throw new IllegalArgumentException("Negative values are not allowed to set fares for passenger type");
        }
        this.fareForPassengerType.putIfAbsent(passengerType, fare);
    }

    public Set<MetroCard> getPassengerTravelHistory() {
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
    public boolean existsById(PassengerType entity) {
        return false;
    }
}
