package com.geektrust.backend.service;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;

public interface IMetroStationService<MetroStation> {
    MetroStation getMetroStation(String metroStation);
    boolean isReturnJourney(MetroCard passengerCard);
    void doCheckInProgress(String passengerCard, String passengerType, String arrivedFrom) throws Exception;
    void printSummary();
}
