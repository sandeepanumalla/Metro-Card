package com.geektrust.backend.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.geektrust.backend.entities.MetroStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

@DisplayName("MetroStation Repository Test")
public class MetroStationRepositoryTest {

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

    @DisplayName("test if SetUpMetroStations() able to save metroStations properly")
    @Test
    public void testSetUpMetroStations() {

        // Test that the method adds a metro station to the list
        MetroStation station1 = new MetroStation("001", "Central Station", 10000, 5000);
        MetroStation station2 = new MetroStation("002", "North Station", 5000, 2000);
        MetroStation station3 = new MetroStation("003", "South Station", 8000, 4000);
        MetroStation station4 = new MetroStation("004", "West Station", 8000, 4000);

        metroStationRepository.setUpMetroStations(station1);
        assertEquals(1, metroStationRepository.countStations());

        // Test that the method adds multiple metro stations to the list
        metroStationRepository.setUpMetroStations(station2);
        metroStationRepository.setUpMetroStations(station3);
        assertEquals(3, metroStationRepository.countStations());

        // Test that the method adds the correct metro stations to the list
        metroStationRepository.setUpMetroStations(station4);
        assertEquals(4, metroStationRepository.countStations());
    }

    @Test
    void testFindShouldReturnEmptyOptionalIfNotPresent() throws Exception {
        String metroStationName = "AIRPORT";
        Optional<MetroStation> metroStation = metroStationRepository.find(metroStationName);
        assertFalse(metroStation.isPresent());
    }

    @Test
    void testCountStations() {
        MetroStation station4 = new MetroStation("004", "West Station", 8000, 4000);
        metroStationRepository.save(station4);

        int numberOfStations = metroStationRepository.countStations();

        assertEquals(1, numberOfStations);
    }

    @Test
    void testFindAll() {
        MetroStation station4 = new MetroStation("004", "West Station", 8000, 4000);
        metroStationRepository.save(station4);

        List<MetroStation> listOfMetroStations = metroStationRepository.findAll();
        assertEquals(1, listOfMetroStations.size());
    }


}
