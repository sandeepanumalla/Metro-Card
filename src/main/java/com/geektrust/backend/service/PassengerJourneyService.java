package com.geektrust.backend.service;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;

import java.util.HashMap;
import java.util.Map;

public class PassengerJourneyService implements IPassengerJourneyService{
    private final IPassengerJourneyRepository passengerJourneyRepository;
    public PassengerJourneyService(IPassengerJourneyRepository passengerJourneyRepository) {
        this.passengerJourneyRepository = passengerJourneyRepository;
    }

    @Override
    public boolean isReturnJourney(MetroCard metroCard) {
        boolean isReturnJourney = passengerJourneyRepository.getPassengerTravelHistory().contains(metroCard);
        passengerJourneyRepository.updatePassengerTravelHistory(metroCard);
        return isReturnJourney;
    }

    @Override
    public Map<PassengerType, Integer> createPassengerTypesMap(Map<MetroCard, Integer> passengersTravelledSummary) {
        Map<PassengerType, Integer> passengerTypesMap = new HashMap<>();

        for (Map.Entry<MetroCard, Integer> entry : passengersTravelledSummary.entrySet()) {
            PassengerType passengerType = entry.getKey().getPassengerType();
            passengerTypesMap.compute(passengerType, (k, v) -> (v == null) ? entry.getValue() : v + entry.getValue());
        }
        return passengerTypesMap;
    }
}
