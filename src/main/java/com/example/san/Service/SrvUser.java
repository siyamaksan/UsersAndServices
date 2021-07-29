package com.example.san.Service;

import com.example.san.Model.DAO.IDaoUser;
import com.example.san.Model.BaseModel.San_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SrvUser {

//    @Qualifier("IDaoUser")
//    @Autowired
//    private ISan_Crud ISAN_crud;

    @Autowired
    private IDaoUser daoUser;


    public San_User Save(San_User user) {

        return (San_User) daoUser.Save(user);

    }


    public San_User getUserByUserName(String userName) {

        return (San_User) daoUser.findByUserName(userName);

    }


}
