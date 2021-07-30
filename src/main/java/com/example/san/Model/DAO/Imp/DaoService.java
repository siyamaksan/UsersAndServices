package com.example.san.Model.DAO.Imp;


import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.DAO.IDaoService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class DaoService extends San_Crud implements IDaoService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public San_Service getById(long serviceId) {
        Query query = entityManager.createQuery("select service from SERVICE service where service.Id=:serviceId");
        query.setParameter("serviceId", serviceId);

        San_Service service = (San_Service) query.getSingleResult();
        return service;
    }

    @Override
    public List<San_Service> findByName(String name) {
        Query query = entityManager.createQuery("select service from SERVICE service where service.ServiceName=:name");
        query.setParameter("name", name);

        List<San_Service> services = (List<San_Service>) query.getResultList();
        return services;
    }

}
