package com.example.san.Model.DAO;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IDaoService extends ISan_Crud{

    San_Service getById(long serviceId);

    List<San_Service> findByName(String name);

}
