package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;

import java.util.*;

public class MetroCardRepository implements IMetroCardRepository<MetroCard, String>{
    Map<String, MetroCard> metroCards = new HashMap<>();

    @Override
    public void save(MetroCard entity) {
        if(existsById(entity)){
            metroCards.put(entity.getName(), entity);
        }
        else{
            throw new MetroCardNotFoundException();
        }
    }

    @Override
    public List<MetroCard> findAll() {
        return new ArrayList<>(metroCards.values());
    }

    @Override
    public Optional<MetroCard> findById(String id) {
        return Optional.ofNullable(metroCards.get(id));
    }

    public Optional<MetroCard> find(String name){
        return Optional.of(metroCards.get(name));
    }

    @Override
    public boolean existsById(MetroCard entity) {
        return !metroCards.containsKey(entity.getName());
    }
    
}
