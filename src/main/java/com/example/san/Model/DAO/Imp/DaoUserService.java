package com.example.san.Model.DAO.Imp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_UserService;
import com.example.san.Model.DAO.IDaoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class DaoUserService extends San_Crud implements IDaoUserService   {
    @Autowired
    private EntityManager entityManager;


    @Override
    public San_UserService findByUserAndService(long serviceId, long userId) {
        Query query = entityManager.createQuery("select us from San_UserService us where us.Service.Id=:s and us.User.Id=:u");
        query.setParameter("s", serviceId);
        query.setParameter("u", userId);


        San_UserService userServices = (San_UserService) query.getSingleResult();
        return userServices;    }
}
