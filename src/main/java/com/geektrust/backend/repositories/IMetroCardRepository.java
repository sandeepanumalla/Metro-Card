package com.geektrust.backend.repositories;

import java.util.Optional;

public interface IMetroCardRepository<MetroCard, String> extends CRUDRepository<MetroCard, String>{
    Optional<MetroCard> find(String name);
}
