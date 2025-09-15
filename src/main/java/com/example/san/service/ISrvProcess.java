package com.example.san.service;

import com.example.san.model.baseModel.User;
import com.example.san.model.baseModel.UserService;
import com.example.san.model.bussiness.ActionResult;

public interface ISrvProcess {


    User increaseUserCredit(long userId, long amount);

    User decreaseUserCredit(long userId, long amount);

    ActionResult invokeService(long serviceId, long userId);


  UserService addUserToService(long userId, long SeviceId);

    ActionResult getUserProcessHistory(Long userId);

    ActionResult getAllProcessHistory();


}
