package com.example.san.Util;

import com.example.san.Service.Security.CustomUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  public static String getCurrentUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (authentication != null) ? authentication.getName() : null;
  }

  public static Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof CustomUserPrincipal userDetails) {
      return userDetails.getId();
    }
    return null;
  }
}