package com.geektrust.backend.services;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;
import com.geektrust.backend.repositories.PassengerJourneyRepository;
import com.geektrust.backend.service.IPassengerJourneyService;
import com.geektrust.backend.service.PassengerJourneyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class PassengerJourneyServiceTest {
    @Mock
    IPassengerJourneyService passengerJourneyService;

    @Test
    public void should_ReturnFalse_IfPassengerTravelsForFirstTime(){
        String passengerCard = "MC5";
        long balance = 600;
        MetroCard metroCard = new MetroCard(passengerCard, balance, PassengerType.ADULT);
        boolean result = passengerJourneyService.isReturnJourney(metroCard);
        assertFalse(result);
    }

    @Test
    public void testIsReturnJourney_returnsTrueWhenPassengerTakenMetroServicePreviously() {
        // Arrange
        IPassengerJourneyRepository passengerJourneyRepository = new PassengerJourneyRepository();
        PassengerJourneyService passengerJourneyService = new PassengerJourneyService(passengerJourneyRepository);
        MetroCard metroCard = new MetroCard("test", 200L);
        passengerJourneyRepository.updatePassengerTravelHistory(metroCard);

        // Act
        boolean isReturnJourney = passengerJourneyService.isReturnJourney(metroCard);

        // Assert
        assertTrue(isReturnJourney);
    }

    @Test
    public void should_RemovePassengerFromReturnList_IfJourneyIsSecondTime() {
        // Arrange
        IPassengerJourneyRepository passengerJourneyRepository = new PassengerJourneyRepository();
        PassengerJourneyService passengerJourneyService = new PassengerJourneyService(passengerJourneyRepository);
        MetroCard metroCard = new MetroCard("A", 200L);
        passengerJourneyRepository.updatePassengerTravelHistory(metroCard);

        // Act
        boolean isReturnJourney = passengerJourneyService.isReturnJourney(metroCard);

        // Assert
        assertTrue(isReturnJourney);
        assertFalse(passengerJourneyRepository.getPassengerTravelHistory().contains(metroCard));
    }

    @Test
    void testCreatePassengerTypesMap() {
        // Arrange
        IPassengerJourneyRepository mockRepository = mock(IPassengerJourneyRepository.class);
        IPassengerJourneyService passengerJourneyService = new PassengerJourneyService(mockRepository);
        MetroCard metroCard1 = new MetroCard("123", 100L, PassengerType.SENIOR_CITIZEN);
        MetroCard metroCard2 = new MetroCard("456", 50L, PassengerType.SENIOR_CITIZEN);
        Map<MetroCard, Integer> passengersTravelledSummary = new HashMap<>();
        passengersTravelledSummary.put(metroCard1, 3);
        passengersTravelledSummary.put(metroCard2, 5);

        // Act
        Map<PassengerType, Integer> result = passengerJourneyService.createPassengerTypesMap(passengersTravelledSummary);

        // Assert
        assertEquals(1, result.size());
        assertEquals(8, result.get(PassengerType.SENIOR_CITIZEN));
    }

    @Test
    void testCreatePassengerTypesMap_emptyInput() {
        // Arrange
        IPassengerJourneyRepository mockRepository = mock(IPassengerJourneyRepository.class);
        IPassengerJourneyService passengerJourneyService = new PassengerJourneyService(mockRepository);
        Map<MetroCard, Integer> passengersTravelledSummary = new HashMap<>();

        // Act
        Map<PassengerType, Integer> result = passengerJourneyService.createPassengerTypesMap(passengersTravelledSummary);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreatePassengerTypesMap_duplicateKeys() {
        // Arrange
        IPassengerJourneyRepository mockRepository = mock(IPassengerJourneyRepository.class);
        IPassengerJourneyService passengerJourneyService = new PassengerJourneyService(mockRepository);
        MetroCard metroCard1 = new MetroCard("123", 100L, PassengerType.SENIOR_CITIZEN);
        MetroCard metroCard2 = new MetroCard("123", 50L, PassengerType.SENIOR_CITIZEN);
        Map<MetroCard, Integer> passengersTravelledSummary = new HashMap<>();
        passengersTravelledSummary.put(metroCard1, 3);
        passengersTravelledSummary.put(metroCard2, 5);

        // Act
        Map<PassengerType, Integer> result = passengerJourneyService.createPassengerTypesMap(passengersTravelledSummary);

        // Assert
        assertEquals(1, result.size());
        assertEquals(8, result.get(PassengerType.SENIOR_CITIZEN));
    }
}
