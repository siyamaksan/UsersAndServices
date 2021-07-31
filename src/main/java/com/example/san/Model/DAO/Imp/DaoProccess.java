package com.example.san.Model.DAO.Imp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_proccess;
import com.example.san.Model.DAO.IDaoProcess;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class DaoProccess extends San_Crud implements IDaoProcess {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Class getDomainClass() {
        return San_proccess.class;
    }


    @Override
    public List<San_proccess> getUserProccess(String username) {
        try {
            Query query = entityManager.createQuery("select p from San_proccess p where p.user.username=:un");
            query.setParameter("un", username);

            List<San_proccess> services= query.getResultList();
            return services;
        }catch (Exception e){
            return  null;
        }

    }

    @Override
    public List<San_proccess> getAllProccess() {
        try {
            Query query = entityManager.createQuery("select p from San_proccess p");

            List<San_proccess> services= query.getResultList();
            return services;
        }catch (Exception e){
            return  null;
        }
    }

}
