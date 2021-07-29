package com.example.san.Service.Security;

import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.DAO.Imp.DaoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private DaoUser daoUser;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        San_User user=daoUser.findByUserName(s);

        if (user==null){
            throw  new UsernameNotFoundException("not found");
        }
        return new  UserService(user);
    }
}
