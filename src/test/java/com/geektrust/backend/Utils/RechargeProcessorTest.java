package com.geektrust.backend.Utils;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.repositories.IMetroCardRepository;
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
}
