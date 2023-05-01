package com.geektrust.backend.data;

import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;

import java.util.HashMap;
import java.util.Map;

public class PassengerFaresLoader {

    final IPassengerJourneyRepository passengerJourneyRepository;
    private final Map<PassengerType, Integer> passengerFaresMap = new HashMap<>();
    public PassengerFaresLoader(IPassengerJourneyRepository passengerJourneyRepository) {
        this.passengerJourneyRepository = passengerJourneyRepository;
    }
    public void loadPassengerFares() {
        passengerFaresMap.put(PassengerType.ADULT, 200);
        passengerFaresMap.put(PassengerType.SENIOR_CITIZEN, 100);
        passengerFaresMap.put(PassengerType.KID, 50);

        for(Map.Entry<PassengerType, Integer> entry: passengerFaresMap.entrySet()) {
            passengerJourneyRepository.setFareByPassengerType(entry.getValue(), entry.getKey());
        }
    }
}
