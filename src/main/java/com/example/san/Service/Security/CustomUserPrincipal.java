package com.example.san.Service.Security;


import com.example.san.Model.BaseModel.User;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Custom UserPrincipal
@Getter
@AllArgsConstructor
public class CustomUserPrincipal implements UserDetails {

  private Long id;
  private String username;
  private String password;
  private String email;
  private Collection<? extends GrantedAuthority> authorities;
  private boolean enabled;
  private boolean accountNonExpired;

  public static CustomUserPrincipal create(User user) {
    List<GrantedAuthority> authorities = user.getAuthorities().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString().toUpperCase()))
        .collect(Collectors.toList());

    return new CustomUserPrincipal(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getEmail(),
        authorities,
        user.getIsActive(),
        user.getAccountNonExpired()
    );
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
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
    return enabled;
  }
}