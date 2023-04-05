package com.geektrust.backend.service;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;

public interface IMetroStationService {
    public MetroStation getMetroStation(String metroStation);
    public boolean isReturnJourney(MetroCard passengerCard);
    public void doCheckInProgress(String passengerCard, String passengerType, String arrivedFrom) throws Exception;
    public void printSummary();
}
