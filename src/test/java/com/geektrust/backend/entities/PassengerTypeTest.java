package com.geektrust.backend.entities;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PassengerTypeTest {

    @Test
    public void testPassengerTypeAdult() {
        PassengerType passengerType = PassengerType.ADULT;
        Assertions.assertEquals("ADULT", passengerType.toString());
    }

    @Test
    public void testPassengerTypeSeniorCitizen() {
        PassengerType passengerType = PassengerType.SENIOR_CITIZEN;
        Assertions.assertEquals("SENIOR_CITIZEN", passengerType.toString());
    }

    @Test
    public void testPassengerTypeKid() {
        PassengerType passengerType = PassengerType.KID;
        Assertions.assertEquals("KID", passengerType.toString());
    }

    @Test
    public void testPassengerTypeValues() {
        PassengerType[] passengerTypes = PassengerType.values();
        Assertions.assertEquals(3, passengerTypes.length);
        Assertions.assertEquals(PassengerType.ADULT, passengerTypes[0]);
        Assertions.assertEquals(PassengerType.SENIOR_CITIZEN, passengerTypes[1]);
        Assertions.assertEquals(PassengerType.KID, passengerTypes[2]);
    }

    @Test
    public void testPassengerTypeValueOf() {
        PassengerType passengerType = PassengerType.valueOf("ADULT");
        Assertions.assertEquals(PassengerType.ADULT, passengerType);
    }

    @Test
    public void testPassengerTypeValueOfInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> PassengerType.valueOf("INVALID"));
    }
}

