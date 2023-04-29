package com.geektrust.backend.repositories;

import java.util.Optional;

public interface IMetroStationRepository<MetroStation, String> extends CRUDRepository<MetroStation>{
    int countStations();
     Optional<MetroStation> find(String name);
     void setUpMetroStations(MetroStation metroStation);
}
