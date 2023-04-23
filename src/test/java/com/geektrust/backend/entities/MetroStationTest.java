package com.geektrust.backend.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MetroStationTest {

    @DisplayName("")
      @Test
      public void should_IncrementPassengers_IfAlreadyPresent_InTravelledSummaryList(){
        String originStation = "AIRPORT";
        long balance = 600;
        MetroCard metroCard = new MetroCard("MC2", balance, PassengerType.ADULT);
        Map<MetroCard, Integer> passengerTravelledSummaryList = new HashMap<MetroCard, Integer>(){
          {
            put(metroCard, 1);
          }
        };
        MetroStation metroStation = new MetroStation("1", originStation, 0, 0);
        metroStation.setPassengersTravelledSummary(passengerTravelledSummaryList);
        metroStation.updatePassengersTravelledSummary(metroCard);
        int result = metroStation.getPassengersTravelledSummary().get(metroCard);

        assertEquals(2, result);
      }


      @DisplayName("should increment passengers if not present in travelled summary list")
      @Test
      public void should_IncrementPassengers_IfNotPresent_InTravelledSummaryList(){
        String originStation = "AIRPORT";
        long balance = 600;
        MetroCard metroCard = new MetroCard("MC2", balance, PassengerType.ADULT);
        MetroStation metroStation = new MetroStation("1", originStation, 0, 0);
        metroStation.updatePassengersTravelledSummary(metroCard);
        int result = metroStation.getPassengersTravelledSummary().get(metroCard);
        assertEquals(1, result);
      }
}
