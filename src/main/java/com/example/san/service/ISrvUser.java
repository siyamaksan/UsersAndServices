package com.example.san.service;

import com.example.san.model.bussiness.ActionResult;


public interface ISrvUser {

    ActionResult save(String username, String password, Boolean isAdmin);

    ActionResult remove(long userId);

    ActionResult edit(long id, String userName, String password);

    ActionResult getAll();

    ActionResult getUserByUserName(String userName);




}
