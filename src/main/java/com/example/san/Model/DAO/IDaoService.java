package com.example.san.Model.DAO;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_UserService;

import java.util.List;

public interface IDaoService extends ISan_Crud{


    List<San_UserService> getUserService(long name);


    List<San_Service> findByName(String name);
}
