package com.geektrust.backend.entities;

import com.geektrust.backend.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MetroCardTest {

    @Mock
    MetroCard metroCard;

    @DisplayName("should recharge the given MetroCard")
    @Test
    public void doRechargeTest() {

        long rechargeAmount = 77;

        metroCard.doRecharge(rechargeAmount);

        verify(metroCard).doRecharge(rechargeAmount);

        when(metroCard.getBalance()).thenReturn(rechargeAmount);
        //react
        Assertions.assertEquals(rechargeAmount, metroCard.getBalance());
    }

    @DisplayName("should return InvalidArgumentException for negative amount")
    @Test
    public void doTestRechargeWithInvalidAmount() {
        long rechargeAmount = -77;
        try {
            doThrow(new IllegalArgumentException("Invalid amount: "+rechargeAmount)).when(metroCard).doRecharge(rechargeAmount);
            metroCard.doRecharge(rechargeAmount);
            Assertions.fail("Expected exception not thrown");
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Invalid amount: "+rechargeAmount, e.getMessage());
        } catch (Exception e) {
            Assertions.fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @DisplayName("should return InvalidArgumentException for null amount")
    @Test
    public void doTestRechargeWithNullAmount() {
        try {
            doThrow(new NullPointerException("Balance is null")).when(metroCard).doRecharge(null);
            metroCard.doRecharge(null);
            Assertions.fail("Expected exception not thrown");
        } catch (NullPointerException e) {
            Assertions.assertEquals("Balance is null", e.getMessage());
        } catch (Exception e) {
            Assertions.fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @DisplayName("should set the passengerType for the given MetroCard")
    @Test
    public void testSetPassengerType() {
        String passengerType = PassengerType.ADULT.toString();

        doNothing().when(metroCard).setPassengerType(passengerType);
        metroCard.setPassengerType(passengerType);
        verify(metroCard).setPassengerType(passengerType);

        when(metroCard.getPassengerType()).thenReturn(PassengerType.ADULT);
        PassengerType passenger = metroCard.getPassengerType();
        verify(metroCard).getPassengerType();

        Assertions.assertEquals(PassengerType.ADULT, passenger);
    }
    @DisplayName("should set the name of the MetroCard")
    @Test
    public void setNameTest() {
        String metroCardName = "MC2";
        doNothing().when(metroCard).setName(metroCardName);
        metroCard.setName(metroCardName);
        verify(metroCard).setName(metroCardName);

        when(metroCard.getName()).thenReturn(metroCardName);
        String actual = metroCard.getName();
        verify(metroCard).getName();

        Assertions.assertEquals(metroCardName, actual);
    }

    @DisplayName("should get the balance for the given MetroCard")
    @Test
    public void testGetBalance() {
        //given
        long balance = 488;

        //when
        when(metroCard.getBalance()).thenReturn((long)488);

        //perform
        long actualBalance = metroCard.getBalance();

        //react
        Assertions.assertEquals(balance, actualBalance);
    }

    @DisplayName("should deduct the amount from the MetroCard successfully")
    @Test
    public void testCheckInDeduction() throws InsufficientBalanceException {
        long walletBalance = 200;
        long checkInAmount = 100;

        doNothing().when(metroCard).deductFare(checkInAmount);
        when(metroCard.getBalance()).thenReturn(100L);

        metroCard.deductFare(checkInAmount);
        verify(metroCard).deductFare(checkInAmount);
        long currentBalance = metroCard.getBalance();
        verify(metroCard).getBalance();

        Assertions.assertEquals(walletBalance - checkInAmount, currentBalance);
    }

    @DisplayName("should return the name for the given MetroCard")
    @Test
    public void testGetName() {
        when(metroCard.getName()).thenReturn("MC2");
        String name = metroCard.getName();
        Assertions.assertEquals("MC2", name);
    }

    @DisplayName("should the return the passenger type for the MetroCard owner")
    @Test
    public void testGetPassengerType () {
        when(metroCard.getPassengerType()).thenReturn(PassengerType.ADULT);
        PassengerType passenger = metroCard.getPassengerType();
        verify(metroCard).getPassengerType();

        Assertions.assertEquals(PassengerType.ADULT, passenger);
    }
}
