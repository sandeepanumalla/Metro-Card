package com.geektrust.backend.services;

import com.geektrust.backend.Utils.DiscountCalculator;
import com.geektrust.backend.Utils.RechargeProcessor;
import com.geektrust.backend.data.LoadProperties;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.repositories.IMetroStationRepository;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;
import com.geektrust.backend.repositories.MetroStationRepository;
import com.geektrust.backend.repositories.PassengerJourneyRepository;
import com.geektrust.backend.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
//public class MetroStationServiceTest {
//
//    @Mock
//    private MetroCardService metroCardServiceMock;
//
//    @Mock
//    private PassengerJourneyRepository passengerJourneyRepository;
//    @Mock
//    private MetroStationRepository metroStationRepositoryMock;
//
//    @Mock
//    private LoadProperties mockProperties;
//
//    @InjectMocks
//    private MetroStationService metroStationServiceMock;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void shouldDoCheckInProcess() {
//        // given
//        // String passengerCard = "MC3";
//        // String passengerType = "SENIOR_CITIZEN";
//        // String originStation = "AIRPORT";
//        // long balance = 25;
//        // MetroStation metroStation = new MetroStation("1", originStation, 0, 0);
//        // MetroCard metroCard = new MetroCard(passengerCard, balance);
//        // Map<MetroCard, Integer> passengerReturnJourneyList = new HashMap<MetroCard, Integer>(){
//        //   {
//        //     put(metroCard, 1);
//        //   }
//        // };
//        // metroStation.updatePassengersTravelledSummary(metroCard);
//
//
//        // doReturn(Optional.ofNullable(metroCard)).when(metroCardServiceMock)
//        //                                         .getMetroCard(anyString());
//
//        // doReturn(passengerReturnJourneyList).when(metroStationRepositoryMock)
//        //                                     .getPassengersReturnJourney();
//
//        // metroStationServiceMock.doCheckInProgress(passengerCard, passengerType, originStation);
//
//        // // long totalCollections = metroStation.getCollections();
//        // // long totalDiscounts = metroStation.getTotalDiscounts();
//        // int passengerCount = metroStation.getPassengersTravelledSummary().get(metroCard);
//        // // assertEquals(101, totalCollections);
//        // // assertEquals(50, totalDiscounts);
//        // assertEquals(2, passengerCount);
//
//    }
//
//    @Test
//    public void testGetMetroStation() {
//        // Create a mock IMetroStationRepository
//
//        // Create a test MetroStation object
//        String testStationName = "Test Station";
//        MetroStation testStation = new MetroStation("004", "West Station", 8000, 4000);
//
//
//        // Set up mock repository to return the test MetroStation object when find() is called with the test station name
//        when(metroStationRepositoryMock.find(testStationName)).thenReturn(Optional.of(testStation));
//
//        // Create a MetroStationService object with the mock repository
//        MetroStationService service = new MetroStationService(metroStationRepositoryMock, null, null, null, null, null);
//
//        // Test scenario 1: Valid metroStation argument
//        MetroStation result = service.getMetroStation(testStationName);
//        assertEquals(testStation, result);
//
//        // Test scenario 2: Invalid metroStation argument
//        String invalidStationName = "Invalid Station";
//        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> service.getMetroStation(invalidStationName));
//        assertEquals(invalidStationName + " metro station not found", exception.getMessage());
//    }
//
//}
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

    private int percentConversionFactor = 50;
    private long serviceFeePercentage = 2L;

    IMetroStationService<MetroStation> metroStationService;

    @BeforeEach
    public void MetroStationServiceSetup() {
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




}
