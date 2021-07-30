package com.example.san.Service.SrvImp;

import com.example.san.Model.DAO.IDaoUser;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Service.ISrvUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SrvUser implements ISrvUser {

    @Autowired
    private IDaoUser daoUser;

    @Override
    public San_User Save(San_User user) {

        return (San_User) daoUser.Save(user);

    }

    @Override
    public San_User remove(San_User user) {
        return null;
    }

    @Override
    public San_User edit(San_User user) {
        return null;
    }

    @Override
    public San_User getAll(San_User user) {
        return null;
    }

    @Override
    public San_User getUserByUserName(String userName) {

        return (San_User) daoUser.findByUserName(userName);

    }

    @Override
    public San_User chargingUser(San_User user) {
        return null;
    }

    @Override
    public San_User addUserToService(San_User user) {
        return null;
    }


}
