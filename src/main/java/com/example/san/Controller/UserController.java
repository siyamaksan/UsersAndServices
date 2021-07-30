package com.example.san.Controller;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Service.SrvImp.SrvUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private SrvUser srvUser;

    @RequestMapping(value = "/newUser")
    public void createNewUser(@RequestParam String username,
                                 @RequestParam String password
                                 ) {
        srvUser.Save(username,password);
    }

    @RequestMapping(value = "/remove")
    public void removeUser(@RequestParam long serviceId) {
        srvUser.remove(serviceId);
    }

    @RequestMapping(value = "/update")
    public San_User updateUser(@RequestParam long userId,
                                  @RequestParam String username,
                                  @RequestParam String password) {


        return srvUser.edit(userId,username,password);
    }

    @RequestMapping(value = "/getAll")
    public List<San_User> getAllUsers() {


        return srvUser.getAll();
    }




    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public San_User getUser(@RequestParam String UserName) {

        return srvUser.getUserByUserName(UserName);

    }


}
