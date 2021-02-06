package com.ccand99.learnreactor;


public interface BlockingRepository<T> {
    void save(T value);

    T findFirst();

    Iterable<T> findAll();

    T findById(String id);
}
