package com.example.san.Controller;

import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Service.SrvImp.SrvUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private SrvUser srvUser;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public ActionResult createNewUser(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam(required = false, defaultValue = "false") Boolean isAdmin
    ) {
        return srvUser.save(username, password, isAdmin);

    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ActionResult removeUser(@RequestParam long serviceId) {
        return srvUser.remove(serviceId);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ActionResult updateUser(@RequestParam long userId,
                                   @RequestParam String username,
                                   @RequestParam String password) {


        return srvUser.edit(userId, username, password);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ActionResult getAllUsers() {


        return srvUser.getAll();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public ActionResult getUser(@RequestParam String UserName) {

        return srvUser.getUserByUserName(UserName);

    }


}
