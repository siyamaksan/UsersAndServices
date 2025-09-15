package com.example.san.Controller.utils;

import com.example.san.Controller.DTO.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * کلاس کمکی برای ساخت پاسخ‌های API
 */
public class ResponseBuilder {
    
    /**
     * ساخت پاسخ موفقیت‌آمیز
     */
    public static <T> ResponseEntity<T> success(T data) {
        return ResponseEntity.ok(data);
    }
    
    /**
     * ساخت پاسخ موفقیت‌آمیز با کد وضعیت سفارشی
     */
    public static <T> ResponseEntity<T> success(T data, HttpStatus status) {
        return ResponseEntity.status(status).body(data);
    }
    
    /**
     * ساخت پاسخ خطا
     */
    public static <T> ResponseEntity<T> error(T data, HttpStatus status) {
        return ResponseEntity.status(status).body(data);
    }
    
    /**
     * ساخت پاسخ خطای احراز هویت
     */
    public static ResponseEntity<LoginResponse> authenticationError(String message) {
        LoginResponse response = LoginResponse.builder()
            .success(false)
            .message(message)
            .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
    /**
     * ساخت پاسخ خطای اعتبارسنجی
     */
    public static ResponseEntity<LoginResponse> validationError(String message) {
        LoginResponse response = LoginResponse.builder()
            .success(false)
            .message(message)
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * ساخت پاسخ خطای سرور
     */
    public static ResponseEntity<LoginResponse> serverError(String message) {
        LoginResponse response = LoginResponse.builder()
            .success(false)
            .message(message)
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
