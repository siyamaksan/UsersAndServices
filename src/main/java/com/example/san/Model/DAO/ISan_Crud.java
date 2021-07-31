package com.example.san.Model.DAO;


import java.util.List;

public interface ISan_Crud<T, E> {

    <E> E Save(T entity);

    <E> E Update(T entity);

    void Delete(T entity);

    List<E> getAll(T entity);

    List<E> getAllActive(T entity);

    <E> E getById(long entityId);
}
