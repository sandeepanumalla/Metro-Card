package com.geektrust.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroPassenger;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.repositories.MetroStationRepository;
import com.geektrust.backend.service.MetroCardService;
import com.geektrust.backend.service.MetroStationService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MetroStationServiceTest {
 
    @Mock
    private MetroCardService metroCardServiceMock;

    @Mock
    private MetroStationRepository metroStationRepositoryMock;

    @InjectMocks
    private MetroStationService metroStationServiceMock;

    @Test
    public void shouldDoCheckInProcess() throws Exception{
        // given
        // String passengerCard = "MC3";
        // String passengerType = "SENIOR_CITIZEN";
        // String arrivedFromStation = "AIRPORT";
        // long balance = 25;
        // MetroStation metroStation = new MetroStation("1", arrivedFromStation, 0, 0);
        // MetroCard metroCard = new MetroCard(passengerCard, balance);
        // Map<MetroCard, Integer> passengerReturnJourneyList = new HashMap<MetroCard, Integer>(){
        //   {
        //     put(metroCard, 1);
        //   }
        // };
        // metroStation.updatePassengersTravelledSummary(metroCard);

       
        // doReturn(Optional.ofNullable(metroCard)).when(metroCardServiceMock)
        //                                         .getMetroCard(anyString());

        // doReturn(passengerReturnJourneyList).when(metroStationRepositoryMock)
        //                                     .getPassengersReturnJourney();

        // metroStationServiceMock.doCheckInProgress(passengerCard, passengerType, arrivedFromStation);

        // // long totalCollections = metroStation.getCollections();
        // // long totalDiscounts = metroStation.getTotalDiscounts();
        // int passengerCount = metroStation.getPassengersTravelledSummary().get(metroCard);
        // // assertEquals(101, totalCollections);
        // // assertEquals(50, totalDiscounts);
        // assertEquals(2, passengerCount);

    }
    
    @Test
    public void should_ReturnFalse_IfPassengerTravelsForFirstTime(){
        String passengerCard = "MC5";
        long balance = 600;
        MetroCard metroCard = new MetroCard(passengerCard, balance, MetroPassenger.ADULT);
        boolean result = metroStationServiceMock.isReturnJourney(metroCard);
        assertEquals(false, result);
    }


    @Test
    public void should_ReturnTrue_IfPassengerTravelsForSecondTime(){
        String passengerCard = "MC5";
        long balance = 600;
        MetroCard metroCard = new MetroCard(passengerCard, balance, MetroPassenger.ADULT);
        Map<MetroCard, Integer> passengerReturnJourneyList = new HashMap<MetroCard, Integer>(){
          {
            put(metroCard, 1);
          }
        };

        when(metroStationRepositoryMock.getPassengersReturnJourney()).
        thenReturn(passengerReturnJourneyList);

        boolean result = metroStationServiceMock.isReturnJourney(metroCard);
        assertEquals(true, result);
    }
}
