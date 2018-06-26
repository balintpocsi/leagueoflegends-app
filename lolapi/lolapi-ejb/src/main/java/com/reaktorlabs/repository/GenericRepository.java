package com.reaktorlabs.repository;

import java.util.List;

/**
 *
 * @author Viki
 */
public interface GenericRepository<K, T> {
    void create(T entity);
    T read(K id);
    List<T> readAll();
    T update(T entity);
    T delete(T entity);
    T deleteById(K id);
}