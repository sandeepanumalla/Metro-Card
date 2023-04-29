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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}
