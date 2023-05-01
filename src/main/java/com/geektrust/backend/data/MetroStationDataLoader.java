package com.geektrust.backend.data;

import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.repositories.IMetroStationRepository;

public class MetroStationDataLoader {

    private final IMetroStationRepository<MetroStation, String> metroStationRepository;
    public MetroStationDataLoader(IMetroStationRepository<MetroStation, String> passengerJourneyRepository) {
        this.metroStationRepository = passengerJourneyRepository;
    }
    public void loadMetroStationsData() {
        metroStationRepository.setUpMetroStations(new MetroStation("1", "CENTRAL", 0, 0));
        metroStationRepository.setUpMetroStations(new MetroStation("2", "AIRPORT", 0,0));
    }
}
