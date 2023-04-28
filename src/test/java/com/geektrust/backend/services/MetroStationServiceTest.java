package com.geektrust.backend.services;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.repositories.MetroStationRepository;
import com.geektrust.backend.repositories.PassengerJourneyRepository;
import com.geektrust.backend.service.MetroCardService;
import com.geektrust.backend.service.MetroStationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MetroStationServiceTest {
 
    @Mock
    private MetroCardService metroCardServiceMock;

    @Mock
    private PassengerJourneyRepository passengerJourneyRepository;
    @Mock
    private MetroStationRepository metroStationRepositoryMock;

    @InjectMocks
    private MetroStationService metroStationServiceMock;

    @Test
    public void shouldDoCheckInProcess() {
        // given
        // String passengerCard = "MC3";
        // String passengerType = "SENIOR_CITIZEN";
        // String originStation = "AIRPORT";
        // long balance = 25;
        // MetroStation metroStation = new MetroStation("1", originStation, 0, 0);
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

        // metroStationServiceMock.doCheckInProgress(passengerCard, passengerType, originStation);

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
        MetroCard metroCard = new MetroCard(passengerCard, balance, PassengerType.ADULT);
        boolean result = metroStationServiceMock.isReturnJourney(metroCard);
        assertFalse(result);
    }


    @Test
    public void should_ReturnTrue_IfPassengerTravelsForSecondTime(){
        String passengerCard = "MC5";
        long balance = 600;
        MetroCard metroCard = new MetroCard(passengerCard, balance, PassengerType.ADULT);
        Map<MetroCard, Integer> passengerReturnJourneyList = new HashMap<MetroCard, Integer>(){
          {
            put(metroCard, 1);
          }
        };

        when(passengerJourneyRepository.getPassengerTravelHistory()).thenReturn(passengerReturnJourneyList);

        boolean result = metroStationServiceMock.isReturnJourney(metroCard);
        assertTrue(result);
    }
}
