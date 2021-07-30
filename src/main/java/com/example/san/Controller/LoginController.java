package com.example.san.Controller;

import com.example.san.Config.SpringSecurityConfig;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Service.Security.CustomeAuthenticationProvider;
import com.example.san.Service.Security.CustomeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
