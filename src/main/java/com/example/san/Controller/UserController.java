package com.example.san.Controller;

import com.example.san.Model.BaseModel.San_User;
import com.example.san.Service.SrvImp.SrvUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    private SrvUser srvUser;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public San_User Save(Principal principal) {
        San_User user = new San_User();
        return srvUser.Save(user);

    }


    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public San_User getUser(@RequestParam String UserName) {

        return srvUser.getUserByUserName(UserName);

    }

    @RequestMapping(value = "/charging", method = RequestMethod.GET)
    public San_User increaseUserCredite(@RequestParam String UserName) {

        return srvUser.getUserByUserName(UserName);

    }
}
