package com.example.san.Service;

import com.example.san.Model.Bussiness.ActionResult;


public interface ISrvUser {

    ActionResult save(String username, String password, Boolean isAdmin);

    ActionResult remove(long userId);

    ActionResult edit(long id, String userName, String password);

    ActionResult getAll();

    ActionResult getUserByUserName(String userName);




}
