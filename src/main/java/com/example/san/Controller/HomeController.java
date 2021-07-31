package com.example.san.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Principal principal) {
        return "login.html";
    }


    @RequestMapping(value = "/swgger", method = RequestMethod.GET)
    public String swagger(Principal principal) {
        return "/wagger-ui.html";
    }

}
