package com.example.san.Service.Security;

import com.example.san.Model.BaseModel.User;
import com.example.san.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Custom UserDetailsService
@Service
@Transactional(readOnly = true)
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userDao;

  public CustomUserDetailsService(UserRepository userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("Loading user by username: {}", username);

    try {
      User user = userDao.findByUsername(username);

      if (user == null) {
        log.warn("User not found: {}", username);
        throw new UsernameNotFoundException("User not found: " + username);
      }

      if (!user.getIsActive()) {
        log.warn("User account is disabled: {}", username);
        throw new DisabledException("User account is disabled: " + username);
      }

      return CustomUserPrincipal.create(user);

    } catch (Exception e) {
      log.error("Error loading user: {}", username, e);
      throw new UsernameNotFoundException("Error loading user: " + username, e);
    }
  }
}