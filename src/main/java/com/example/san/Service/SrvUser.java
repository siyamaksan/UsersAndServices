package com.example.san.Service;

import com.example.san.Model.DAO.ISAN_Crud;
import com.example.san.Model.SAN_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SrvUser {

    @Autowired
    private ISAN_Crud ISAN_crud;

    public SAN_User Save(SAN_User user) {

        return (SAN_User) ISAN_crud.Save(user);

    }
}
