package com.example.san.Model.DAO;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;


public interface IDaoUser extends ISan_Crud {

    San_User findByUserName(String UserName);




}
