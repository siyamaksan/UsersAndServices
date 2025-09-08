package com.example.san.Controller;


import com.example.san.Model.BaseModel.User;
import com.example.san.Service.Security.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import java.security.Principal;

@RestController
public class LoginController {



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin(@RequestParam String username, @RequestParam String password, Principal principal) {
        // read principal out of security context and set it to session
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        User loggedInUser = ((UserService) authentication.getPrincipal()).getUserDetails();

        return "Logged In!!!";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(SessionStatus session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        return "Logged Out!!!";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    public String doLogin() {
        return "/wagger-ui.html";
    }




    private void validatePrinciple(Object principal) {
        if (!(principal instanceof UserDetailsService)) {
            throw new IllegalArgumentException("Principal can not be null!");
        }
    }
}
