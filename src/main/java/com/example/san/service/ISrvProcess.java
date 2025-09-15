package com.example.san.service;

import com.example.san.model.baseModel.SanProcess;
import com.example.san.model.baseModel.User;
import com.example.san.model.baseModel.UserService;
import com.example.san.model.bussiness.ActionResult;
import java.util.List;

public interface ISrvProcess {


    User increaseUserCredit(long userId, long amount);

    User decreaseUserCredit(long userId, long amount);

    Object invokeService(long serviceId, long userId);


  UserService addUserToService(long userId, long SeviceId);

    List<SanProcess> getUserProcessHistory(Long userId);

    ActionResult getAllProcessHistory();


}
