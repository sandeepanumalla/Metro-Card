package com.geektrust.backend.services;

import com.geektrust.backend.Utils.DiscountCalculator;
import com.geektrust.backend.Utils.RechargeProcessor;
import com.geektrust.backend.data.LoadProperties;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.exceptions.InsufficientBalanceException;
import com.geektrust.backend.repositories.IMetroStationRepository;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;
import com.geektrust.backend.repositories.MetroStationRepository;
import com.geektrust.backend.repositories.PassengerJourneyRepository;
import com.geektrust.backend.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MetroStationServiceTest {

    @Mock
    IMetroStationRepository<MetroStation, String> metroStationRepository;

    @Mock
    IMetroCardService<MetroCard> metroCardService;

    @Mock
    IPassengerJourneyService passengerJourneyService;

    @Mock
    IPassengerJourneyRepository passengerJourneyRepository;

    @Mock
    DiscountCalculator discountCalculator;

    @Mock
    RechargeProcessor rechargeProcessor;

    IMetroStationService<MetroStation> metroStationService;

    @BeforeEach
    public void MetroStationServiceSetup() {
        int percentConversionFactor = 100;
        long serviceFeePercentage = 2L;
        metroStationService = new MetroStationService(
                metroStationRepository,
                metroCardService,
                passengerJourneyService,
                passengerJourneyRepository,
                discountCalculator,
                rechargeProcessor,
                percentConversionFactor,
                serviceFeePercentage
        );
    }

    @Test
    void shouldCorrectlyCalculateJourneyFare_IfReturnJourney() {
        MetroCard metroCard = new MetroCard("MC1", 50L, PassengerType.SENIOR_CITIZEN);
        MetroStation station4 = new MetroStation("004", "AIRPORT", 8000, 4000);
        when(passengerJourneyRepository.getFareByPassengerType(metroCard.getPassengerType())).thenReturn(100L);
        when(metroStationRepository.find(anyString())).thenReturn(Optional.of(station4));
        when(discountCalculator.calculateDiscount(100L, true, station4)).thenReturn(50L);
        long journeyAmount = metroStationService.calculateJourneyFare(metroCard, "AIRPORT",true);

        verify(passengerJourneyRepository, times(1)).getFareByPassengerType(metroCard.getPassengerType());
        verify(metroStationRepository, times(1)).find(anyString());
        Assertions.assertEquals(50L, journeyAmount);
    }

    @Test
    void shouldCorrectlyCalculateJourneyFare_IfNotReturnJourney() {
        MetroCard metroCard = new MetroCard("MC1", 50L, PassengerType.SENIOR_CITIZEN);
        MetroStation station4 = new MetroStation("004", "AIRPORT", 8000, 4000);
        when(passengerJourneyRepository.getFareByPassengerType(metroCard.getPassengerType())).thenReturn(100L);
        when(metroStationRepository.find(anyString())).thenReturn(Optional.of(station4));
        when(discountCalculator.calculateDiscount(100L, false, station4)).thenReturn(0L);
        long journeyAmount = metroStationService.calculateJourneyFare(metroCard, "AIRPORT",false);

        verify(passengerJourneyRepository, times(1)).getFareByPassengerType(metroCard.getPassengerType());
        verify(metroStationRepository, times(1)).find(anyString());
        Assertions.assertEquals(100L, journeyAmount);
    }

//    @Test
//    public void  testUpdatePassengerJourneyProcessIfBalanceIsNotEnough() throws InsufficientBalanceException {
//        String passengerCard = "MC1";
//        String originStation = "AIRPORT";
//
//        boolean isReturn = false;
//        MetroCard metroCard = new MetroCard("MC1", 50L, PassengerType.SENIOR_CITIZEN);
//
//        MetroStation station4 = new MetroStation("004", "AIRPORT", 8000, 4000);
//        when(metroCardService.getMetroCard(metroCard.getName())).thenReturn(Optional.of(metroCard));
//        when(passengerJourneyRepository.getFareByPassengerType(metroCard.getPassengerType())).thenReturn(100L);
//        when(metroStationRepository.find(originStation)).thenReturn(Optional.of(station4));
//        when(discountCalculator.calculateDiscount(100L, false, station4)).thenReturn(0L);
//        when(rechargeProcessor.rechargePassengerMetroCard(metroCard,51L)).thenReturn((long)(metroCard.getBalance() + 51L));
//        doReturn(101L).when(metroCard).getBalance();
//        metroStationService.deductJourneyFare(passengerCard, originStation, isReturn);
//
//        verify(rechargeProcessor, times(1)).rechargePassengerMetroCard(metroCard, anyLong());
//        verify(station4, times(1)).updateCollections(anyLong());
//    }

//    @Test
//    public void testDeductJourneyFare() throws InsufficientBalanceException {
//        // Arrange
//        String passengerCard = "MC1";
//        String originStation = "STATION1";
//        boolean isReturnJourney = false;
//        long journeyAmount = 100L;
//        long balance = 50L;
//        long rechargeAmount = journeyAmount - balance;
//        long transactionFee = Math.round((float) (rechargeAmount * 2) / 100);
//        long totalAmount = journeyAmount + transactionFee;
//
//        MetroCard metroCard = new MetroCard(passengerCard, balance, PassengerType.SENIOR_CITIZEN);
//        MetroStation metroStation = new MetroStation("STATION1", "Station 1", 100, 100);
//        when(discountCalculator.calculateDiscount(anyLong(), isReturnJourney, metroStation)).thenReturn(0L);
//        when(metroStationRepository.find(anyString())).thenReturn(Optional.of(metroStation));
//        when(metroCardService.getMetroCard(passengerCard)).thenReturn(Optional.of(metroCard));
//        when(metroStationService.calculateJourneyFare(metroCard, originStation, isReturnJourney)).thenReturn(journeyAmount);
//
//        ArgumentCaptor<Long> rechargeAmountCaptor = ArgumentCaptor.forClass(Long.class);
//        doNothing().when(rechargeProcessor).rechargePassengerMetroCard(eq(metroCard), rechargeAmountCaptor.capture());
//
//        // Act
//        metroStationService.deductJourneyFare(passengerCard, originStation, isReturnJourney);
//
//        // Assert
//        long actualRechargeAmount = rechargeAmountCaptor.getValue();
//        Assertions.assertEquals(rechargeAmount + transactionFee, actualRechargeAmount);
//
//        verify(rechargeProcessor).rechargePassengerMetroCard(eq(metroCard), eq(rechargeAmount + transactionFee));
//        verify(metroStation).updateCollections(eq(transactionFee + journeyAmount));
//        verify(metroCard).deductFare(eq(totalAmount));
//    }

}
