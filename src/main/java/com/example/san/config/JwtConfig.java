package com.example.san.config;


import com.example.san.service.Security.JwtAuthenticationFilter;
import com.example.san.service.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// JWT Configuration (Optional - for API authentication)
@Configuration
@ConditionalOnProperty(name = "security.jwt.enabled", havingValue = "true")
public class JwtConfig {

  @Value("${security.jwt.secret:mySecretKey}")
  private String jwtSecret;

  @Value("${security.jwt.expiration:86400}")
  private int jwtExpiration;

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Bean
  public JwtTokenProvider jwtTokenProvider() {
    return new JwtTokenProvider(jwtSecret, jwtExpiration);
  }
}