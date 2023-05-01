package com.geektrust.backend.Utils;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RechargeProcessorTest {

    @Mock
    MetroCard metroCard;

    @InjectMocks
    RechargeProcessor rechargeProcessor;


    @DisplayName("should do recharge")
    @Test
    public void testRechargeProcessor() {
        long rechargeAmount = 100;
        Mockito.when(metroCard.getBalance()).thenReturn(rechargeAmount);
        long rechargedAmount = rechargeProcessor.rechargePassengerMetroCard(metroCard, rechargeAmount);
        Mockito.verify(metroCard).doRecharge(rechargeAmount);
        Mockito.verify(metroCard).getBalance();
        Assertions.assertEquals(100, rechargedAmount);
    }

    @DisplayName("should not do recharge if recharge amount is negative")
    @Test
    public void testRechargeProcessorWithNegativeAmount() {
        // Arrange
        long balance = 400L;
        MetroCard metroCard = new MetroCard("MC2", balance, PassengerType.ADULT);
        RechargeProcessor rechargeProcessor = new RechargeProcessor();

        // Act
        long rechargeAmount = -50;
        Assertions.assertThrows(IllegalArgumentException.class, () -> rechargeProcessor.rechargePassengerMetroCard(metroCard, rechargeAmount));
    }

    @DisplayName("should not do recharge if recharge amount is zero")
    @Test
    public void testRechargeProcessorWithZeroAmount() {
        // Arrange
        long balance = 400L;
        MetroCard metroCard = new MetroCard("MC2", balance, PassengerType.ADULT);
        RechargeProcessor rechargeProcessor = new RechargeProcessor();

        // Act
        long rechargeAmount = 0;
        long rechargedAmount = rechargeProcessor.rechargePassengerMetroCard(metroCard, rechargeAmount);

        // Assert
        Assertions.assertEquals(400L, rechargedAmount);
        Assertions.assertEquals(balance, metroCard.getBalance());
    }

    @DisplayName("should do recharge if current balance is negative")
    @Test
    public void testRechargeProcessorWithNegativeBalance() {
        // Arrange
        long balance = 400L;
        MetroCard metroCard = new MetroCard("MC2", balance, PassengerType.ADULT);
        RechargeProcessor rechargeProcessor = new RechargeProcessor();

        // Act
        long rechargeAmount = 100;
//        long rechargedAmount = rechargeProcessor.rechargePassengerMetroCard(metroCard, rechargeAmount);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> metroCard.doRecharge(-50L));
//        Assertions.assertThrows(rechargeAmount - 50L, () -> rechargeProcessor.rechargePassengerMetroCard(metroCard, rechargeAmount));
    }

    @DisplayName("should do recharge if current balance is zero")
    @Test
    public void testRechargeProcessorWithZeroBalance() {
        // Arrange
        long balance = 400L;
        MetroCard metroCard = new MetroCard("MC2", balance, PassengerType.ADULT);
        RechargeProcessor rechargeProcessor = new RechargeProcessor();
        metroCard.doRecharge(0L);

        // Act
        long rechargeAmount = 100;
        long rechargedAmount = rechargeProcessor.rechargePassengerMetroCard(metroCard, rechargeAmount);

        // Assert
        Assertions.assertEquals(rechargeAmount + balance, rechargedAmount);
        Assertions.assertEquals(rechargeAmount + balance, metroCard.getBalance());
    }

}
