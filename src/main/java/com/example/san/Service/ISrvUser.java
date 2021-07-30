package com.example.san.Service;

import com.example.san.Model.BaseModel.San_User;

import java.util.List;


public interface ISrvUser {

    San_User Save(String username,String password);

    Boolean remove(long userId);

    San_User edit(long id,String userName,String password);

    List<San_User> getAll();

    San_User getUserByUserName(String userName);




}
