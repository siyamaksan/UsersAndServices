package com.example.san.Controller;

import com.example.san.Model.BaseModel.San_User;
import com.example.san.Service.SrvUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/User")
public class UserController {

    @Autowired
    private SrvUser srvUser;

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public San_User Save() {

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IOC_SpringConfig.class);
        San_User user = new San_User();

        return srvUser.Save(user);

    }

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public San_User getUser(@RequestParam String UserName) {

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IOC_SpringConfig.class);

        return srvUser.getUserByUserName(UserName);

    }
}
