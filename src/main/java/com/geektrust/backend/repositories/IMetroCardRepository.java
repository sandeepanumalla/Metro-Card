package com.geektrust.backend.repositories;

import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;

public interface IMetroCardRepository<MetroCard, String> extends CRUDRepository<MetroCard, String>{
    public Optional<MetroCard> find(String name);
}
