package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PassengerJourneyRepositoryTest {
    PassengerJourneyRepository passengerJourneyRepository;

    @BeforeEach
    void setup() {
        passengerJourneyRepository = new PassengerJourneyRepository();
    }

    @Test
    @DisplayName("Setting fare for a new passenger type")
    void testSetFareByPassengerType_newPassengerType() throws NoSuchElementException {
        PassengerType passengerType1 = PassengerType.ADULT;
        int fare1 = 200;
        passengerJourneyRepository.setFareByPassengerType(fare1, passengerType1);
        assertEquals(fare1, passengerJourneyRepository.getFareByPassengerType(passengerType1));
    }

    @Test
    @DisplayName("Setting fare for a new passenger type with a different value")
    void testSetFareByPassengerType_newPassengerTypeDifferentValue() throws NoSuchElementException {
        PassengerType passengerType2 = PassengerType.SENIOR_CITIZEN;
        int fare2 = 100;
        passengerJourneyRepository.setFareByPassengerType(fare2, passengerType2);
        assertEquals(fare2, passengerJourneyRepository.getFareByPassengerType(passengerType2));
    }

    @Test
    @DisplayName("Setting fare for a new passenger type with a negative value")
    void testSetFareByPassengerType_newPassengerTypeNegativeValue() {
        PassengerType passengerType3 = PassengerType.KID;
        int fare3 = -50;
        assertThrows(IllegalArgumentException.class, () -> passengerJourneyRepository.setFareByPassengerType(fare3, passengerType3));

        assertThrows(NoSuchElementException.class, () -> passengerJourneyRepository.getFareByPassengerType(passengerType3));
    }

    @Test
    void testingSettingFareForPassengerType() throws NoSuchElementException {
        PassengerJourneyRepository repository = new PassengerJourneyRepository();
        repository.setFareByPassengerType(30, PassengerType.ADULT);
        assertEquals(30, repository.getFareByPassengerType(PassengerType.ADULT));
    }

    @Test
    void testUpdatingTravelHistoryForPassenger() {
        PassengerJourneyRepository repository = new PassengerJourneyRepository();
        MetroCard metroCard = new MetroCard("123", 400L, PassengerType.ADULT);
        repository.updatePassengerTravelHistory(metroCard);
        assertTrue(repository.getPassengerTravelHistory().contains(metroCard));
    }

    @Test
    void testingUpdateForExistingPassengers() {
        PassengerJourneyRepository repository = new PassengerJourneyRepository();
        MetroCard metroCard = new MetroCard("123", 400L, PassengerType.ADULT);
        repository.updatePassengerTravelHistory(metroCard);
        repository.updatePassengerTravelHistory(metroCard);
        assertFalse(repository.getPassengerTravelHistory().contains(metroCard));
    }


    @Test
    void testingGetFareForNonExistingPassengers() throws NoSuchElementException {
        PassengerJourneyRepository repository = new PassengerJourneyRepository();
        assertThrows(NoSuchElementException.class, () -> repository.getFareByPassengerType(PassengerType.ADULT));
    }


    @Test
    void testSavingNewEntity() {
        PassengerJourneyRepository repository = new PassengerJourneyRepository();
        PassengerType entity = PassengerType.KID;
        repository.setFareByPassengerType(50, entity);
        assertEquals(50, repository.getFareByPassengerType(entity));
    }

    @Test
    public void testUpdatePassengerTravelHistory() {
        // Add a metro card to the travel history
        MetroCard metroCard = new MetroCard("12345", 200L);
        passengerJourneyRepository.updatePassengerTravelHistory(metroCard);

        // Check if the travel history contains the added metro card
        assertTrue(passengerJourneyRepository.getPassengerTravelHistory().contains(metroCard));

        // Remove the metro card from the travel history
        passengerJourneyRepository.updatePassengerTravelHistory(metroCard);

        // Check if the travel history does not contain the removed metro card
        assertFalse(passengerJourneyRepository.getPassengerTravelHistory().contains(metroCard));
    }

    @Test
    void testGettingPassengerTravelHistory() {
        PassengerJourneyRepository repository = new PassengerJourneyRepository();
        MetroCard metroCard1 = new MetroCard("123", 200L, PassengerType.ADULT);
        MetroCard metroCard2 = new MetroCard("456", 300L, PassengerType.SENIOR_CITIZEN);
        repository.updatePassengerTravelHistory(metroCard1);
        repository.updatePassengerTravelHistory(metroCard2);
        Set<MetroCard> passengerTravelHistory = repository.getPassengerTravelHistory();
        assertEquals(2, passengerTravelHistory.size());
        assertTrue(passengerTravelHistory.contains(metroCard1));
        assertTrue(passengerTravelHistory.contains(metroCard2));
    }

    @Test
    void testGettingPassengerTravelHistoryWhenEmpty() {
        PassengerJourneyRepository repository = new PassengerJourneyRepository();
        Set<MetroCard> passengerTravelHistory = repository.getPassengerTravelHistory();
        assertTrue(passengerTravelHistory.isEmpty());
    }
}
