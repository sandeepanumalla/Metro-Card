package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.MetroStation;

public class MetroStationRepository implements IMetroStationRepository<MetroStation, String>{

    private final List<MetroStation> listOfMetroStations = new ArrayList<MetroStation>(){
        {
            add(new MetroStation("1", "CENTRAL", 0, 0));
            add(new MetroStation("2", "AIRPORT", 0, 0));
        }
    };

    @Override
    public void save(MetroStation entity) {
        if(!existsById(entity)){
            listOfMetroStations.add(entity);
        }
    }


//    public Map<MetroCard, Integer> getPassengersReturnJourney() {
//        return passengersReturnJourney;
//    }
//
//    public void updatePassengerInReturnList( MetroCard metroCard) {
//        if(this.getPassengersReturnJourney().containsKey(metroCard)){
//            this.getPassengersReturnJourney().remove(metroCard);
//        }
//        else{
//            this.getPassengersReturnJourney().putIfAbsent(metroCard, 1);
//        }
//    }

    @Override
    public List<MetroStation> findAll() {
        return listOfMetroStations;
    }

    @Override
    public Optional<MetroStation> findById(String id) {
        return listOfMetroStations.stream().filter(each -> each.getId().equals(id)).findAny();
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
}

//    @Override
//    public boolean existsById(MetroStation entity) {
//        return !listOfMetroStations.stream().filter(e -> e.getId().equals(entity.getId()))
//                .findAny()
//                .isPresent();
//    }
