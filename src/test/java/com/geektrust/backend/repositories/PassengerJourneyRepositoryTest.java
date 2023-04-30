package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.PassengerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void testSetFareByPassengerType_newPassengerType() {
        PassengerType passengerType1 = PassengerType.ADULT;
        int fare1 = 200;
        passengerJourneyRepository.setFareByPassengerType(fare1, passengerType1);
        assertEquals(fare1, passengerJourneyRepository.getFareByPassengerType(passengerType1));
    }


    @Test
    @DisplayName("Setting fare for a new passenger type with a different value")
    void testSetFareByPassengerType_newPassengerTypeDifferentValue() {
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

        assertThrows(NullPointerException.class, () -> passengerJourneyRepository.getFareByPassengerType(passengerType3));
    }
}
