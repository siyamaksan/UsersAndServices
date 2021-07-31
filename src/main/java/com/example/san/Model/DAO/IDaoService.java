package com.example.san.Model.DAO;

import com.example.san.Model.BaseModel.San_Service;

import java.util.List;

public interface IDaoService extends ISan_Crud{


    List<San_Service> getUserService(String name);


    List<San_Service> findByName(String name);
}
