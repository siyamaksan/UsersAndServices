package com.example.san.controller;

import com.example.san.model.dto.LoginRequest;
import com.example.san.model.dto.LoginResponse;
import com.example.san.exception.GeneralException;
import com.example.san.enums.GeneralStatus;
import com.example.san.enums.UserStatus;
import com.example.san.exception.UserException;
import com.example.san.controller.utils.ResponseBuilder;
import com.example.san.model.bussiness.ActionResult;
import com.example.san.model.baseModel.User;
import com.example.san.service.Security.JwtTokenProvider;
import com.example.san.service.srvImp.SrvUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SrvUser srvUser;

    /**
     * ورود کاربر و دریافت JWT Token
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        
        // احراز هویت کاربر
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), 
                loginRequest.getPassword()
            )
        );

        // تولید JWT Token
        String jwt = jwtTokenProvider.generateToken(authentication);
        
        // تنظیم Cookie برای JWT
        setJwtCookie(response, jwt, loginRequest.isRememberMe());
        
        // دریافت اطلاعات کاربر
        User user = getUserFromAuthentication(authentication);
        
        // ساخت پاسخ موفقیت‌آمیز
        LoginResponse loginResponse = LoginResponse.builder()
            .success(true)
            .message("ورود موفقیت‌آمیز")
            .token(jwt)
            .tokenType("Bearer")
            .expiresIn(3600)
            .user(createUserInfo(user))
            .build();

        return ResponseBuilder.success(loginResponse);
    }

    /**
     * خروج کاربر و پاک کردن Token
     */
    @PostMapping("/logout")
    public ResponseEntity<LoginResponse> logout(HttpServletResponse response) {
        // پاک کردن Cookie
        clearJwtCookie(response);

        LoginResponse logoutResponse = LoginResponse.builder()
            .success(true)
            .message("خروج موفقیت‌آمیز")
            .build();

        return ResponseBuilder.success(logoutResponse);
    }

    /**
     * تازه‌سازی Token
     */
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestParam String token) {
        try {
            String newToken = jwtTokenProvider.refreshToken(token);
            
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", true);
            responseBody.put("message", "Token تازه‌سازی شد");
            responseBody.put("token", newToken);
            responseBody.put("tokenType", "Bearer");
            responseBody.put("expiresIn", 3600);

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            log.error("Error refreshing token: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Token نامعتبر است");
            errorResponse.put("error", "INVALID_TOKEN");
            
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    /**
     * بررسی وضعیت Token
     */
    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestParam String token) {
        boolean isValid = jwtTokenProvider.validateToken(token);
        
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("valid", isValid);
        
        if (isValid) {
            responseBody.put("username", jwtTokenProvider.getUsernameFromToken(token));
            responseBody.put("userId", jwtTokenProvider.getUserIdFromToken(token));
            responseBody.put("authorities", jwtTokenProvider.getAuthoritiesFromToken(token));
        }
        
        return ResponseEntity.ok(responseBody);
    }

    /**
     * دریافت اطلاعات کاربر فعلی
     */
    @GetMapping("/me")
    public ResponseEntity<ActionResult> getCurrentUser(Authentication authentication) {
        try {
            String username = authentication.getName();
            ActionResult result = srvUser.getUserByUserName(username);
            
            if (!result.isSuccessful()) {
                throw new UserException(
                    UserStatus.USER_NOT_FOUND, "کاربر با نام کاربری " + username + " یافت نشد");
            }
            
            return ResponseEntity.ok(result);
        } catch (UserException e) {
            throw e; // Re-throw UserException to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            log.error("Error getting user info: {}", e.getMessage());
            throw new GeneralException(GeneralStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ========== متدهای کمکی ==========

    /**
     * پاک کردن JWT Cookie
     */
    private void clearJwtCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("JWT-TOKEN", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
    }

    /**
     * تنظیم JWT Cookie
     */
    private void setJwtCookie(HttpServletResponse response, String jwt, boolean rememberMe) {
        Cookie jwtCookie = new Cookie("JWT-TOKEN", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); // در production باید true باشد
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(rememberMe ? 7 * 24 * 3600 : 3600); // 7 روز یا 1 ساعت
        response.addCookie(jwtCookie);
    }

    /**
     * دریافت User از Authentication
     */
    private User getUserFromAuthentication(Authentication authentication) {
        try {
            String username = authentication.getName();
            ActionResult result = srvUser.getUserByUserName(username);
            
            if (!result.isSuccessful()) {
                throw new UserException(
                    UserStatus.USER_NOT_FOUND, "کاربر با نام کاربری " + username + " یافت نشد");
            }
            
            if (!(result.getDetail() instanceof User)) {
                throw new UserException(UserStatus.USER_NOT_FOUND, "اطلاعات کاربر نامعتبر است");
            }
            
            return (User) result.getDetail();
        } catch (UserException e) {
            throw e; // Re-throw UserException
        } catch (Exception e) {
            log.error("Error getting user info: {}", e.getMessage());
            throw new GeneralException(GeneralStatus.INTERNAL_SERVER_ERROR, "خطا در دریافت اطلاعات کاربر");
        }
    }

    /**
     * ایجاد UserInfo از User
     */
    private LoginResponse.UserInfo createUserInfo(User user) {
        List<String> authorities = user.getAuthorities().stream()
            .map(authority -> authority.getRole().toString())
            .collect(Collectors.toList());

        return LoginResponse.UserInfo.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .authorities(authorities)
            .build();
    }
}