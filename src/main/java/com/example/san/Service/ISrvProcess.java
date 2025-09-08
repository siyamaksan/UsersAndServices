package com.example.san.Service;

import com.example.san.Model.Bussiness.ActionResult;

public interface ISrvProcess {


    ActionResult increaseUserCredit(long userId, long amount);

    ActionResult decreaseUserCredit(long userId, long amount);

    ActionResult invokeService(long serviceId, long userId);


  ActionResult addUserToService(long userId, long SeviceId);

    ActionResult getUserProcessHistory(Long userId);

    ActionResult getAllProcessHistory();


}
