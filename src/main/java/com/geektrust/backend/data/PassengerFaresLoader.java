package com.geektrust.backend.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;
import com.geektrust.backend.repositories.PassengerJourneyRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PassengerFaresLoader {

    final IPassengerJourneyRepository passengerJourneyRepository;
    public PassengerFaresLoader(IPassengerJourneyRepository passengerJourneyRepository) {
        this.passengerJourneyRepository = passengerJourneyRepository;
    }
    public void loadPassengerFares() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/fares_for_passenger.json");
        HashMap<String, Integer> passengerFaresMap;
        try {
            passengerFaresMap = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Map.Entry<String, Integer> entry: passengerFaresMap.entrySet()) {
            PassengerType passengerType = PassengerType.valueOf(entry.getKey());
            passengerJourneyRepository.setFareByPassengerType(entry.getValue(), passengerType);
        }
    }
}
