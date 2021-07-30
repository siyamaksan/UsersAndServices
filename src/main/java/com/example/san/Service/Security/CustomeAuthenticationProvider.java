package com.example.san.Service.Security;

import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.DAO.Imp.DaoUser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CustomeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private DaoUser daoUser;


    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication auth) {
        String username = auth.getName();

        if (username != null && !"".equals(username)) {
            San_User user = daoUser.findByUserName(username);

            if (user != null) {
                String password = auth.getCredentials().toString();
                if ((password.equals(user.getPassword()))) {

                    @SuppressWarnings("unchecked")
                    List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) auth.getAuthorities();

                    return new UsernamePasswordAuthenticationToken(
                            user.getUserName(),
                            null,
                            authorities);
                }
                throw new Exception("Passwords is missing or invalid.");
            }
            throw new Exception("There is no user with username = " + username);
        }

        throw new Exception("You did not provide a username.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}