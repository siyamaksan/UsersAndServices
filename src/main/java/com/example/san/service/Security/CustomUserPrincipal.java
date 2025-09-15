package com.example.san.service.Security;


import com.example.san.model.baseModel.User;
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
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().toString().toUpperCase()))
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
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }


  @Override
  public boolean isEnabled() {
    return enabled;
  }
}