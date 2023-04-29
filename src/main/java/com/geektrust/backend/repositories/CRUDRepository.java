package com.geektrust.backend.repositories;

import java.util.List;

public interface CRUDRepository<T> {
    void save(T entity);
    List<T> findAll();
    boolean existsById(T entity);
}
