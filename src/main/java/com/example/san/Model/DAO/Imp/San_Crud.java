package com.example.san.Model.DAO.Imp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;
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
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public abstract class San_Crud<T, E extends Serializable> implements ISan_Crud<T, E> {

    protected Class<T> domainClass = getDomainClass();

    protected abstract Class<T> getDomainClass();

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public <E> E Save(T entity) {
        entityManager.persist(entity);
        return (E) entity;

    }

    @Override
    public <E> E Update(T entity) {
        entityManager.merge(entity);
        return (E) entity;
    }

    @Override
    public void Delete(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public List<E> getAll(T entity) {
        System.out.println(entity.getClass().getEnclosingClass());
        Query q = entityManager.createQuery("select s from " + domainClass.getName() + " s");
        List<E> entities = q.getResultList();
        return (List<E>) entities;
    }

    @Override
    public List<E> getAllActive(T entity) {
        Query query = entityManager.createQuery("select e from " + domainClass.getName() + " e where e.isActive=1");

        List<E> entities = query.getResultList();
        return entities;
    }

    @Override
    public <E> E getById(long entityId) {
        try {
            Query query = entityManager.createQuery("select e from " + domainClass.getName() + " e where e.Id=:id");
            query.setParameter("id", entityId);

            return (E) query.getSingleResult();
        }catch (Exception e){
            List<E> s=new ArrayList<>();
            return (E) s.get(0);
        }

    }
}
