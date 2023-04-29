package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.MetroStation;

public class MetroStationRepository implements IMetroStationRepository<MetroStation, String>{
    private final List<MetroStation> listOfMetroStations = new ArrayList<>();

    @Override
    public void save(MetroStation entity) {
        if(!existsById(entity)){
            listOfMetroStations.add(entity);
        }
    }

    @Override
    public List<MetroStation> findAll() {
        return listOfMetroStations;
    }


    @Override
    public boolean existsById(MetroStation entity) {
        return listOfMetroStations.stream().anyMatch(e -> e.getId().equals(entity.getId()));
    }
    @Override
    public int countStations() {
        return listOfMetroStations.size();
    }

    @Override
    public Optional<MetroStation> find(String name) {
        return listOfMetroStations.stream().filter(str -> str.getName().equals(name))
                                            .findAny();
    }

    @Override
    public void setUpMetroStations(MetroStation metroStation) {
        listOfMetroStations.add(metroStation);
    }
}
