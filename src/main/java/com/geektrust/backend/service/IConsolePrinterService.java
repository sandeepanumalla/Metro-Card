package com.geektrust.backend.service;

import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.entities.PassengerType;

import java.util.List;
import java.util.Map;

public interface IConsolePrinterService {
     void printSummary();
     void printTotalCollection(MetroStation station);
     void printPassengerTypeSummary(MetroStation station);

    void sortPassengers(List<Map.Entry<PassengerType, Integer>> inputList);
}
