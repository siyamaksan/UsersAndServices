package com.example.san.Model.DAO.Imp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.DAO.IDaoUser;
import com.example.san.Model.BaseModel.San_User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Transactional
@Repository
public class DaoUser extends San_Crud implements IDaoUser {


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public San_User findByUserName(String UserName) {
        Query query = entityManager.createQuery("select User from San_User User where User.UserName=:UserName");
        query.setParameter("UserName", UserName);

        San_User user = (San_User) query.getSingleResult();
        return user;
    }

    @Override
    public San_User getById(long userId) {
        Query query = entityManager.createQuery("select user from San_User user where user.Id=:userId");
        query.setParameter("userId", userId);

        San_User user = (San_User) query.getSingleResult();
        return user;
    }
}
