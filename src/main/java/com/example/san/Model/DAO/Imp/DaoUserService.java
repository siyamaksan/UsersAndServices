package com.example.san.Model.DAO.Imp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_UserService;
import com.example.san.Model.DAO.IDaoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class DaoUserService extends San_Crud implements IDaoUserService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public San_UserService findByUserAndService(long serviceId, long userId) {
        try {
            Query query = entityManager.createQuery("select us from San_UserService us where us.Service.id=:s and us.User.id=:u");
            query.setParameter("s", serviceId);
            query.setParameter("u", userId);


            San_UserService userServices = (San_UserService) query.getSingleResult();
            return userServices;
        }catch (Exception e){
            return new San_UserService();
        }

    }

    @Override
    protected Class getDomainClass() {
        return UserDetailsService.class;
    }
}
