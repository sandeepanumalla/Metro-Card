package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.MetroStation;

public class MetroStationRepository implements IMetroStationRepository<MetroStation, String>{

    private final List<MetroStation> LIST_OF_METROSTATIONS = new ArrayList<MetroStation>();

    @Override
    public void save(MetroStation entity) {
        if(!existsById(entity)){
            LIST_OF_METROSTATIONS.add(entity);
        }
    }

    @Override
    public List<MetroStation> findAll() {
        return LIST_OF_METROSTATIONS;
    }

    @Override
    public Optional<MetroStation> findById(String id) {
        return LIST_OF_METROSTATIONS.stream().filter(each -> each.getId().equals(id)).findAny();
    }

    @Override
    public boolean existsById(MetroStation entity) {
        return LIST_OF_METROSTATIONS.stream().anyMatch(e -> e.getId().equals(entity.getId()));
    }
    @Override
    public int countStations() {
        return LIST_OF_METROSTATIONS.size();
    }

    @Override
    public Optional<MetroStation> find(String name) {
        return LIST_OF_METROSTATIONS.stream().filter(str -> str.getName().equals(name))
                                            .findAny();
    }

    @Override
    public void setUpMetroStations(MetroStation metroStation) {
        LIST_OF_METROSTATIONS.add(metroStation);
    }
}
