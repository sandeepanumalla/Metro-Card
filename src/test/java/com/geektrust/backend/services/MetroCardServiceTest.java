package com.geektrust.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.util.Optional;

import com.geektrust.backend.Utils.RechargeProcessor;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.exceptions.InsufficientBalanceException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.repositories.IMetroCardRepository;
import com.geektrust.backend.repositories.MetroStationRepository;
import com.geektrust.backend.repositories.PassengerJourneyRepository;
import com.geektrust.backend.service.MetroCardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MetroCardServiceTest {

    @Mock
    IMetroCardRepository<MetroCard, String> metroCardRepositoryMock;

    @Mock
    MetroStationRepository metroStationRepositoryMock;

    @Mock
    PassengerJourneyRepository passengerJourneyRepository;

    @InjectMocks
    MetroCardService metroCardServiceMock;

    @Mock
    RechargeProcessor rechargeProcessor;

    @Test
    @DisplayName("should recharge the wallet")
    public void should_RechargeWallet(){
        // given
        String metroCardName = "MC1";
        long balance = 150;
        MetroCard metroCard = new MetroCard(metroCardName, balance); 
        Optional<MetroCard> optional = Optional.ofNullable(metroCard);
        long amountToBeRecharged = 200 - balance;
        // when
        when(this.metroCardRepositoryMock.find(anyString())).thenReturn(optional);
        long rechargeAmount = rechargeProcessor.rechargePassengerMetroCard(metroCard, amountToBeRecharged);
        // then
        assertEquals(200, rechargeAmount);
    }

    @Test
    public void should_ThrowException_InRechargeWallet_WhenNullObjectPassed(){
        // given
        String metroCardName = "MC1";
        long balance = 150;
        MetroCard metroCard = new MetroCard(metroCardName, balance); 
        long amountToBeRecharged = 200 - balance;
        // when
        doThrow(new MetroCardNotFoundException(""))
                .when(this.metroCardRepositoryMock)
                .find("MC1");
        // then
        assertThrows(MetroCardNotFoundException.class, () -> 
                rechargeProcessor.rechargePassengerMetroCard(metroCard, amountToBeRecharged));
    }

    @Test
    public void should_RechargeWallet_IfBalanceIs_Less_And_isNotReturnJourney() throws InsufficientBalanceException {
        // given
        String metroCardName = "MC1";
        PassengerType passenger = PassengerType.ADULT;
        long balance = 150;
        String stationName = "CENTRAL";
        MetroCard metroCard = new MetroCard(metroCardName, balance, passenger);
        Optional<MetroCard> optionalMetroCard = Optional.ofNullable(metroCard);
        MetroStation metroStation = new MetroStation("1", stationName, 0, 0);
        Optional<MetroStation> optionalMetroStation = Optional.ofNullable(metroStation);

        // when
        when(metroCardServiceMock.getMetroCard(metroCardName))
                    .thenReturn(optionalMetroCard);
        when(passengerJourneyRepository.getFareByPassengerType(passenger))
        .thenReturn((long)200);
        when(metroStationRepositoryMock.find(stationName))
                .thenReturn(optionalMetroStation);

        // then
        long rechargeAmount = metroCardServiceMock.processJourney(metroCardName, stationName, false);
        long totalCollections = metroStationRepositoryMock.find(stationName).get().getCollections();

        assertEquals(51, rechargeAmount);
        assertEquals(201, totalCollections);
    }

    @Test
    public void should_RechargeWallet_IfBalanceIs_Less_And_isReturnJourney() throws InsufficientBalanceException {
        String metroCardName = "MC1";
        PassengerType passenger = PassengerType.ADULT;
        long balance = 50;
        String stationName = "CENTRAL";
        MetroCard metroCard = new MetroCard(metroCardName, balance, passenger);
        Optional<MetroCard> optionalMetroCard = Optional.ofNullable(metroCard);
        MetroStation metroStation = new MetroStation("1", stationName, 0, 0);
        Optional<MetroStation> optionalMetroStation = Optional.ofNullable(metroStation);
        // when
        when(metroCardServiceMock.getMetroCard(metroCardName))
                    .thenReturn(optionalMetroCard);
        when(metroStationRepositoryMock.find(stationName))
                .thenReturn(optionalMetroStation);
        when(passengerJourneyRepository.getFareByPassengerType(passenger))
        .thenReturn((long)200);

        long rechargeAmount = metroCardServiceMock.processJourney(metroCardName, stationName, true);
        long totalCollections = metroStationRepositoryMock.find(stationName).get().getCollections();
        // then
        assertEquals(51, rechargeAmount);
        assertEquals(101, totalCollections);
    }

    @Test
    public void should_NotRechargeWallet_IfBalanceIs_Sufficient_And_isReturnJourney() throws InsufficientBalanceException {
        String metroCardName = "MC1";
        PassengerType passenger = PassengerType.ADULT;
        long balance = 200;
        String stationName = "CENTRAL";
        MetroCard metroCard = new MetroCard(metroCardName, balance, passenger);
        Optional<MetroCard> optionalMetroCard = Optional.ofNullable(metroCard);
        MetroStation metroStation = new MetroStation("1", stationName, 0, 0);
        Optional<MetroStation> optionalMetroStation = Optional.ofNullable(metroStation);
        // when
        when(metroCardServiceMock.getMetroCard(metroCardName))
                    .thenReturn(optionalMetroCard);
        when(metroStationRepositoryMock.find(stationName))
                .thenReturn(optionalMetroStation);
        when(passengerJourneyRepository.getFareByPassengerType(passenger))
        .thenReturn((long)200);

        long rechargeAmount = metroCardServiceMock.processJourney(metroCardName, stationName, true);
        long totalCollections = metroStationRepositoryMock.find(stationName).get().getCollections();
        // then
        assertEquals(0, rechargeAmount);
        assertEquals(100, totalCollections);

    }

    @Test
    public void should_NotRechargeWallet_IfBalanceIs_Sufficient_And_isNotReturnJourney() throws InsufficientBalanceException {
        String metroCardName = "MC1";
        PassengerType passenger = PassengerType.ADULT;
        long balance = 200;
        String stationName = "CENTRAL";
        MetroCard metroCard = new MetroCard(metroCardName, balance, passenger);
        Optional<MetroCard> optionalMetroCard = Optional.ofNullable(metroCard);
        MetroStation metroStation = new MetroStation("1", stationName, 0, 0);
        Optional<MetroStation> optionalMetroStation = Optional.ofNullable(metroStation);
        // when
        when(metroCardServiceMock.getMetroCard(metroCardName))
                    .thenReturn(optionalMetroCard);
        when(metroStationRepositoryMock.find(stationName))
                .thenReturn(optionalMetroStation);
        when(passengerJourneyRepository.getFareByPassengerType(passenger))
        .thenReturn((long)200);

        when(passengerJourneyRepository.getFareByPassengerType(passenger))
        .thenReturn((long)200);
        long rechargeAmount = metroCardServiceMock.processJourney(metroCardName, stationName, false);
        long totalCollections = metroStationRepositoryMock.find(stationName).get().getCollections();
        // then
        assertEquals(0, rechargeAmount);
        assertEquals(200, totalCollections);
    }
}
