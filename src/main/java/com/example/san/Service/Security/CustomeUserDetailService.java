package com.example.san.Service.Security;

import com.example.san.Model.BaseModel.San_Authority;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.DAO.IDaoUser;
import com.example.san.Model.DAO.Imp.DaoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomeUserDetailService implements UserDetailsService {

    @Autowired
    private IDaoUser iDaoUser;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        San_User user = iDaoUser.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new UserService(user);
    }

}