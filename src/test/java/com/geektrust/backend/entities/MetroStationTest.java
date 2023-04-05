package com.geektrust.backend.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MetroStationTest {

   

      @Test
      public void should_IncrementPassengers_IfAlreadyPresent_InTravelledSummaryList(){
        String arrivedFromStation = "AIRPORT";
        long balance = 600;
        MetroCard metroCard = new MetroCard("MC2", balance, MetroPassenger.ADULT);
        Map<MetroCard, Integer> passengerTravelledSummaryList = new HashMap<MetroCard, Integer>(){
          {
            put(metroCard, 1);
          }
        };
        MetroStation metroStation = new MetroStation("1", arrivedFromStation, 0, 0);
        metroStation.setPassengersTravelledSummary(passengerTravelledSummaryList);
        metroStation.updatePassengersTravelledSummary(metroCard);
        int result = metroStation.getPassengersTravelledSummary().get(metroCard);

        assertEquals(2, result);
      }


      @Test
      public void should_IncrementPassengers_IfNotPresent_InTravelledSummaryList(){
        String arrivedFromStation = "AIRPORT";
        long balance = 600;
        MetroCard metroCard = new MetroCard("MC2", balance, MetroPassenger.ADULT);
        MetroStation metroStation = new MetroStation("1", arrivedFromStation, 0, 0);
        metroStation.updatePassengersTravelledSummary(metroCard);
        int result = metroStation.getPassengersTravelledSummary().get(metroCard);
        assertEquals(1, result);
      }
}
