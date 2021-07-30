package com.example.san.Service.Security;

import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.DAO.Imp.DaoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailService implements UserDetailsService {

    @Autowired
    private DaoUser daoUser;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final San_User user = daoUser.findByUserName(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDetails userDetails = User.withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities("ROLEUSER").build();
        return userDetails;
    }
}
