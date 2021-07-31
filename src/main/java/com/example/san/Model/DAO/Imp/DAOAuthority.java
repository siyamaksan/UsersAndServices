package com.example.san.Model.DAO.Imp;

import com.example.san.Model.BaseModel.San_Authority;
import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.DAO.IDAOAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class DAOAuthority extends San_Crud implements IDAOAuthority {

    @Autowired
    private EntityManager entityManager;



    @Override
    protected Class getDomainClass() {
        return San_Service.class;
    }
}
