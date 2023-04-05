package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;

public class MetroCardRepository implements IMetroCardRepository<MetroCard, String>{
    Map<String, MetroCard> metroCards = new HashMap<>(); 
    Map<String, Long> amountRequired = new HashMap<>();

    @Override
    public MetroCard save(MetroCard entity) {
        if(!existsById(entity)){
            metroCards.put(entity.getName(), entity);
            return entity;
        }
        else{
            throw new MetroCardNotFoundException();
        }
    }

    @Override
    public List<MetroCard> findAll() {
        return metroCards.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<MetroCard> findById(String id) {
        return Optional.ofNullable(metroCards.get(id));
    }

    public Optional<MetroCard> find(String name){
        Optional<MetroCard> optional = Optional.of(metroCards.get(name));
        return optional;
    }

    @Override
    public boolean existsById(MetroCard entity) {
        return metroCards.containsKey(entity.getName());
    }
    
}
