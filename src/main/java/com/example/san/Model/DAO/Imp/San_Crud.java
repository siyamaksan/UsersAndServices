package com.example.san.Model.DAO.Imp;

import com.example.san.Model.DAO.ISan_Crud;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Repository
@Transactional
public abstract class San_Crud<T, E extends Serializable> implements ISan_Crud<T, E> {


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public <E> E Save(T entity) {
        entityManager.persist(entity);
        return (E) entity;

    }

    @Override
    public <E> E Update(T entity) {
        return null;
    }

    @Override
    public <E> E Delete(T entity) {
        return null;
    }

    @Override
    public <E> E GetAll(T entity) {
        return null;
    }


}
