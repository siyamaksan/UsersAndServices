package com.example.san.Model.DAO;

import com.example.san.Model.BaseModel.San_UserService;

public interface IDaoUserService extends ISan_Crud {

    San_UserService findByUserAndService(long serviceId, long userId);
}
