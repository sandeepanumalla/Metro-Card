package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;

import java.util.Map;

public interface IPassengerJourneyRepository extends  CRUDRepository<PassengerType, String> {

    Map<MetroCard, Integer> getPassengerTravelHistory();
    long getFareByPassengerType(PassengerType passenger);
    void setPassengersReturnJourney(Map<MetroCard, Integer> passengersTravelled);
    void updatePassengerTravelHistory( MetroCard metroCard);

    void setFareByPassengerType(int fare, PassengerType passengerType) throws IllegalArgumentException;
}

