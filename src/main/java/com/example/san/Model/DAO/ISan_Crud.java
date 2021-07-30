package com.example.san.Model.DAO;


import java.util.List;

public interface ISan_Crud<T,E> {

    public <E> E Save(T entity);

    public <E> E  Update(T entity);

    public void Delete(T entity);

    public List<E> getAll(T entity);

    public List<E> getAllActive(T entity);








}
