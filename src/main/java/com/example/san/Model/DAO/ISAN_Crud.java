package com.example.san.Model.DAO;


public interface ISAN_Crud<T,E> {

    public <E> E Save(T entity);

    public <E> E Update(T entity);

    public <E> E Delete(T entity);

    public <E> E GetAll(T entity);
    public <E> E findByName(T entity);







}
