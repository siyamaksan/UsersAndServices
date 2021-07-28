package com.example.san.Model.DAO.ISAN_CrudImp;

import com.example.san.Model.DAO.ISAN_Crud;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Repository
@Transactional
public class SAN_Crud<T, E extends Serializable> implements ISAN_Crud<T, E> {

    @PersistenceContext
    private EntityManager entityManager;

//    private Session getSession() {
//
//        if (entityManager.unwrap(Session.class)!=null)
//        return entityManager.unwrap(Session.class);
//        else
//            entityManager.
//    }

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

    @Override
    public <E> E findByName(T entity) {
        return null;
    }
}
