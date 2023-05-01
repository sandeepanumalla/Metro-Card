package com.geektrust.backend.services;

import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.service.ConsolePrinterService;
import com.geektrust.backend.service.IConsolePrinterService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConsolePrinterServiceTest {


    private IConsolePrinterService consolePrinterService;

    @Mock
    private PrintStream mockPrintStream;


    private ArgumentCaptor<String> stringArgumentCaptor;

//    @BeforeEach
//    void setup() {
//        consolePrinterService = new ConsolePrinterService(mockPrintStream, null);
//    }

    @BeforeEach
    void setup() {
        consolePrinterService = new ConsolePrinterService(null, null);
    }

    @Test
    void sortPassengers_withSameValues_sortAlphabetically() {
        List<Map.Entry<PassengerType, Integer>> inputList = new ArrayList<>();
        inputList.add(new AbstractMap.SimpleEntry<>(PassengerType.ADULT, 2));
        inputList.add(new AbstractMap.SimpleEntry<>(PassengerType.KID, 1));
        inputList.add(new AbstractMap.SimpleEntry<>(PassengerType.SENIOR_CITIZEN, 2));

        consolePrinterService.sortPassengers(inputList);

        assertEquals(PassengerType.ADULT, inputList.get(0).getKey());
        assertEquals(PassengerType.SENIOR_CITIZEN, inputList.get(1).getKey());
        assertEquals(PassengerType.KID, inputList.get(2).getKey());
    }


    @Test
    void sortPassengers_allSameValue_sortAlphabetically() {
        List<Map.Entry<PassengerType, Integer>> inputList = new ArrayList<>();
        inputList.add(new AbstractMap.SimpleEntry<>(PassengerType.ADULT, 2));
        inputList.add(new AbstractMap.SimpleEntry<>(PassengerType.KID, 2));
        inputList.add(new AbstractMap.SimpleEntry<>(PassengerType.SENIOR_CITIZEN, 2));

        consolePrinterService.sortPassengers(inputList);

        assertEquals(PassengerType.ADULT, inputList.get(0).getKey());
        assertEquals(PassengerType.KID, inputList.get(1).getKey());
        assertEquals(PassengerType.SENIOR_CITIZEN, inputList.get(2).getKey());
    }

    @Test
    void sortPassengers_descendingValues_sortByValue() {
        List<Map.Entry<PassengerType, Integer>> inputList = new ArrayList<>();
        inputList.add(new AbstractMap.SimpleEntry<>(PassengerType.ADULT, 3));
        inputList.add(new AbstractMap.SimpleEntry<>(PassengerType.KID, 2));
        inputList.add(new AbstractMap.SimpleEntry<>(PassengerType.SENIOR_CITIZEN, 1));

        consolePrinterService.sortPassengers(inputList);

        assertEquals(PassengerType.ADULT, inputList.get(0).getKey());
        assertEquals(PassengerType.KID, inputList.get(1).getKey());
        assertEquals(PassengerType.SENIOR_CITIZEN, inputList.get(2).getKey());
    }

}
