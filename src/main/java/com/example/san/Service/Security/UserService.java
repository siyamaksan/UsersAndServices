package com.example.san.Service.Security;

import com.example.san.Model.DAO.IDaoUser;
import com.example.san.Model.BaseModel.San_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;


public class UserService implements UserDetails {


    private San_User san_user;
    public UserService(San_User san_user) {
        this.san_user = san_user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return san_user.getGroupRoles().stream().map(san_groupRole ->new SimpleGrantedAuthority(san_groupRole.getRole().getRoleName().toString())).collect(Collectors.toList()) ;
    }

    @Override
    public String getPassword() {
        return san_user.getPassword();
    }

    @Override
    public String getUsername() {
        return san_user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
