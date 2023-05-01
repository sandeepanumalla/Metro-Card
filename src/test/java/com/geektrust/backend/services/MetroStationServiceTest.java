package com.geektrust.backend.services;

import com.geektrust.backend.Utils.DiscountCalculator;
import com.geektrust.backend.Utils.RechargeProcessor;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.repositories.IMetroStationRepository;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void shouldCorrectlyCalculateJourneyFare_IfReturnJourney() throws NoSuchMethodException {
        MetroCard metroCard = new MetroCard("MC1", 50L, PassengerType.SENIOR_CITIZEN);
        MetroStation station4 = new MetroStation("004", "AIRPORT", 8000, 4000);
        when(passengerJourneyRepository.getFareByPassengerType(metroCard.getPassengerType())).thenReturn(100L);
        when(metroStationRepository.find(anyString())).thenReturn(Optional.of(station4));
        when(discountCalculator.calculateDiscount(100L, true, station4)).thenReturn(50L);
        long journeyAmount = metroStationService.calculateJourneyFare(metroCard, "AIRPORT",true);

        verify(passengerJourneyRepository, times(1)).getFareByPassengerType(metroCard.getPassengerType());
        verify(metroStationRepository, times(1)).find(anyString());
        assertEquals(50L, journeyAmount);
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
        assertEquals(100L, journeyAmount);
    }


}
