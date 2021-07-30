package com.example.san.Controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
//        validatePrinciple(authentication.getPrincipal());
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
        return "true";
    }




    private void validatePrinciple(Object principal) {
        if (!(principal instanceof UserDetailsService)) {
            throw new IllegalArgumentException("Principal can not be null!");
        }
    }
}
