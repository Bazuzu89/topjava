package ru.javawebinar.topjava.DAO;

import java.util.List;

public interface Repository<T> {
    int create(T entity);
    T getById(int id);
    int update(T entity);
    void delete(int id);
    List<T> getAll();
}
