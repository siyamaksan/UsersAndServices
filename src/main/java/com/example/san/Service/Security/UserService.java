package com.example.san.Service.Security;

import com.example.san.Model.BaseModel.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import java.util.stream.Collectors;


public class UserService implements UserDetails {
    private User _user;

    public UserService(User user) {
        this._user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return _user.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName().toString())).collect(Collectors.toList());
    }

    public long getId() {
        return _user.getId();
    }

    @Override
    public String getPassword() {

        return _user.getPassword();
    }

    @Override
    public String getUsername() {
        return _user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUserDetails() {
        return _user;
    }
}
