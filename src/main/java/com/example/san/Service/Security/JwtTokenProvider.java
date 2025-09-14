package com.example.san.Service.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

  private final int jwtExpirationInMs;
  private final SecretKey secretKey;

  public JwtTokenProvider(@Value("${security.jwt.secret}") String jwtSecret,
      @Value("${security.jwt.expiration}") int jwtExpirationInSec) {
    this.jwtExpirationInMs = jwtExpirationInSec * 1000;
    this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  private String createToken(String subject, Date expiryDate,
      Map<String, Object> additionalClaims) {
    return Jwts.builder()
        .claims(additionalClaims)
        .subject(subject)
        .issuedAt(new Date())
        .expiration(expiryDate)
        .signWith(secretKey )
        .compact();
  }

  public String generateToken(Authentication authentication) {
    CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
    Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationInMs);

    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userPrincipal.getId());
    claims.put("email", userPrincipal.getEmail());
    claims.put("authorities", userPrincipal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList()));

    return createToken(userPrincipal.getUsername(), expiryDate, claims);
  }

  public String generateTokenFromUsername(String username) {
    Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationInMs);
    return createToken(username, expiryDate, Jwts.claims().build());
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build().parseSignedClaims(token)
        .getPayload();
  }

  public String getUsernameFromToken(String token) {
    return getAllClaimsFromToken(token).getSubject();
  }

  public Long getUserIdFromToken(String token) {
    return getAllClaimsFromToken(token).get("userId", Long.class);
  }

  @SuppressWarnings("unchecked")
  public List<String> getAuthoritiesFromToken(String token) {
    return (List<String>) getAllClaimsFromToken(token).get("authorities");
  }

  public Date getExpirationDateFromToken(String token) {
    return getAllClaimsFromToken(token).getExpiration();
  }

  public Date getIssuedAtDateFromToken(String token) {
    return getAllClaimsFromToken(token).getIssuedAt();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(authToken);
      return true;
    } catch (SignatureException ex) {
      log.error("Invalid JWT signature: {}", ex.getMessage());
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token: {}", ex.getMessage());
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token: {}", ex.getMessage());
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token: {}", ex.getMessage());
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty: {}", ex.getMessage());
    }
    return false;
  }

  public String refreshToken(String token) {
    try {
      Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
          .getPayload();
      return generateTokenFromUsername(claims.getSubject());
    } catch (ExpiredJwtException ex) {
      return generateTokenFromUsername(ex.getClaims().getSubject());
    } catch (Exception ex) {
      throw new IllegalArgumentException("Cannot refresh invalid token", ex);
    }
  }

  public boolean isTokenExpired(String token) {
    try {
      return getExpirationDateFromToken(token).before(new Date());
    } catch (ExpiredJwtException ex) {
      return true;
    }
  }
}
