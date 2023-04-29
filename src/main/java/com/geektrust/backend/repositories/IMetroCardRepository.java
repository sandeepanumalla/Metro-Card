package com.geektrust.backend.repositories;

import java.util.Optional;

public interface IMetroCardRepository<MetroCard, String> extends CRUDRepository<MetroCard>{
    Optional<MetroCard> find(String name);
}
