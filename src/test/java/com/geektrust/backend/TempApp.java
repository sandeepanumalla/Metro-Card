package com.geektrust.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geektrust.backend.entities.PassengerType;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TempApp {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = TempApp.class.getResourceAsStream("/fares_for_passenger.json");
        HashMap<String, Integer> passengerFaresMap = new HashMap<>();
        try {
            passengerFaresMap = objectMapper.readValue(inputStream, new TypeReference<HashMap<String, Integer>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Map.Entry<String, Integer> entry: passengerFaresMap.entrySet()) {
            PassengerType passengerType = PassengerType.valueOf(entry.getKey());

        }
        System.out.println(passengerFaresMap.size());
    }
}
