package com.example.san.Model.DAO;

import com.example.san.Model.BaseModel.San_proccess;

import java.util.List;

public interface IDaoProcess extends ISan_Crud{

    List<San_proccess> getUserProccess(String userId);

    List<San_proccess> getAllProccess();


}
