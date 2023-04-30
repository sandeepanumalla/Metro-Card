package com.geektrust.backend.service;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.exceptions.InsufficientBalanceException;

public interface IMetroStationService<MetroStation> {
    MetroStation getMetroStation(String metroStation);

    long calculateJourneyFare(MetroCard metroCard, String originStation, boolean isReturnJourney);

    void deductJourneyFare(String passengerCard, String originStation, boolean isReturnJourney) throws InsufficientBalanceException;

    void updatePassengerJourneyProgress (String passengerCard, String passengerType, String arrivedFrom) throws Exception;

}
