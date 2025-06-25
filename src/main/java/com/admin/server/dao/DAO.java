package com.admin.server.dao;

import java.util.List;

public interface DAO<T> {
    void add(T entity);
    T getById(int id);
    List<T> getAll();
    void update(T entity);
    void delete(int id);
}
