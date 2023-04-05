package com.geektrust.backend.repositories;

import java.util.Optional;
import com.geektrust.backend.entities.MetroStation;

public interface IMetroStationRepository<MetroStation, String> extends CRUDRepository<MetroStation, String>{
    public int countStations(); 
    public Optional<MetroStation> find(String name);
}
