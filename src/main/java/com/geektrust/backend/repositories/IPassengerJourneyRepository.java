package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;

import java.util.NoSuchElementException;
import java.util.Set;

public interface IPassengerJourneyRepository extends  CRUDRepository<PassengerType> {

    Set<MetroCard> getPassengerTravelHistory();
    long getFareByPassengerType(PassengerType passenger) throws NoSuchElementException;
    void updatePassengerTravelHistory( MetroCard metroCard);
    void setFareByPassengerType(int fare, PassengerType passengerType) throws IllegalArgumentException;
}

