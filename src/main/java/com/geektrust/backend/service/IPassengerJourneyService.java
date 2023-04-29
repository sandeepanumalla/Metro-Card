package com.geektrust.backend.service;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import java.util.Map;

public interface IPassengerJourneyService {
     boolean isReturnJourney(MetroCard metroCard);
     Map<PassengerType, Integer> createPassengerTypesMap(Map<MetroCard, Integer> passengersTravelledSummary);
}
