package com.geektrust.backend.repositories;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T, ID> {
    void save(T entity);
    List<T> findAll();
    Optional<T> findById(ID id);
    boolean existsById(T entity);
}
