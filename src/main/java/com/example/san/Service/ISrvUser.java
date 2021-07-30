package com.example.san.Service;

import com.example.san.Model.BaseModel.San_User;
import org.springframework.stereotype.Service;


public interface ISrvUser {

    San_User Save(San_User user);

    San_User remove(San_User user);

    San_User edit(San_User user);

    San_User getAll(San_User user);

    San_User getUserByUserName(String userName);

    San_User chargingUser(San_User user);

    San_User addUserToService(San_User user);



}
