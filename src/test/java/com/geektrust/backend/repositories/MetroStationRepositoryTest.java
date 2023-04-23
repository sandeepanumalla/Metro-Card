package com.geektrust.backend.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.MetroStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

@DisplayName("MetroStation Repository Test")
public class MetroStationRepositoryTest {
    // @Mock
    MetroStationRepository metroStationRepository;

    @BeforeEach
    void setup(){
        metroStationRepository = new MetroStationRepository();
    }

    @Test
    @DisplayName("check if save method is saving the metrostation")
    void saveTest(){
        metroStationRepository = new MetroStationRepository();
        metroStationRepository.save(new MetroStation("1", "Airport", 0, 0));
        metroStationRepository.save(new MetroStation("2", "Central", 0, 0));
        assertEquals(metroStationRepository.countStations(), 2);
    }



//    @Test
//    public void should_AddPassengerToReturnList_IfJourneyIsFirstTime(){
//      String originStation = "AIRPORT";
//      long balance = 600;
//      MetroCard metroCard = new MetroCard("MC2", balance, PassengerType.ADULT);
//    //   MetroStation metroStation = new MetroStation("1", originStation, 0, 0);
//      metroStationRepository.updatePassengerInReturnList(metroCard);
//      boolean actualIsPresent = metroStationRepository.getPassengersReturnJourney()
//                                              .containsKey(metroCard);
//      int size = metroStationRepository.getPassengersReturnJourney()
//                                      .size();
//      assertEquals(true, actualIsPresent);
//      assertEquals(1, size);
//    }

//    @Test
//    public void should_RemovePassengerFromReturnList_IfJourneyIsSecondTime(){
//      long balance = 600;
//      MetroCard metroCard = new MetroCard("MC2", balance, PassengerType.ADULT);
//      Map<MetroCard, Integer> passengerReturnJourneyList = new HashMap<MetroCard, Integer>(){
//        {
//          put(metroCard, 1);
//        }
//      };
//
//      metroStationRepository.setPassengersReturnJourney(passengerReturnJourneyList);
//      metroStationRepository.updatePassengerInReturnList(metroCard);
//      boolean actualIsPresent = metroStationRepository.getPassengersReturnJourney()
//                                                    .containsKey(metroCard);
//      int size = metroStationRepository.getPassengersReturnJourney()
//                                      .size();
//      assertEquals(false, actualIsPresent);
//      assertEquals(0, size);
//    }
//
    void findById(){
        
    }
}
