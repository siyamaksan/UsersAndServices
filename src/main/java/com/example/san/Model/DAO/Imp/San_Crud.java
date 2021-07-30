package com.example.san.Model.DAO.Imp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.DAO.ISan_Crud;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

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
         entityManager.persist(entity);
         return (E) entity;
    }

    @Override
    public void Delete(T entity) {
         entityManager.remove(entity);
    }

    @Override
    public List<E> getAll(T entity) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = (CriteriaQuery<T>) cb.createQuery(entity.getClass());
        Root<T> rootEntry = (Root<T>) cq.from(entity.getClass());
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return (List<E>) allQuery.getResultList();
    }

    @Override
    public List<E> getAllActive(T entity) {
        Query query = entityManager.createQuery("select e from "+entity.getClass().getName()+" e where e.isActive=1");

        List<E> entities =  query.getResultList();
        return entities;
    }
}
