package com.geektrust.backend.service;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.exceptions.InsufficientBalanceException;

import java.util.NoSuchElementException;

public interface IMetroStationService<MetroStation> {
    MetroStation getMetroStation(String metroStation);

    long calculateJourneyFare(MetroCard metroCard, String originStation, boolean isReturnJourney) throws NoSuchElementException;

    void deductJourneyFare(String passengerCard, String originStation, boolean isReturnJourney) throws InsufficientBalanceException, NoSuchMethodException;

    void updatePassengerJourneyProgress (String passengerCard, String passengerType, String arrivedFrom) throws Exception;

}
