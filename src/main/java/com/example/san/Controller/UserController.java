package com.example.san.Controller;

import com.example.san.Model.SAN_User;
import com.example.san.Service.SrvUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private SrvUser srvUser;

    @RequestMapping(value = "/save")
    public SAN_User Save() {


//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IOC_SpringConfig.class);
        SAN_User user = new SAN_User();
        user.setFirstName("siyamak");
        user.setLastName("alizadeh");
        user.setAmount(150000L);

        return srvUser.Save(user);


    }
}
