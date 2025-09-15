package com.example.san.Controller.DTO;

import com.example.san.Controller.Exception.ExceptionCode;
import com.example.san.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO برای پاسخ‌های خطا با پشتیبانی از i18n
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    private boolean success;
    private String errorCode;
    private String message;
    private String details;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, Object> validationErrors;
    
    /**
     * ایجاد ErrorResponse از ErrorCode با MessageSource
     */
    public static ErrorResponse fromErrorCode(UserStatus exceptionCode, MessageSource messageSource) {
        String message = messageSource.getMessage(
            exceptionCode.getMessageKey(),
            null, 
            exceptionCode.getMessageKey(), // fallback
            LocaleContextHolder.getLocale()
        );
        
        return ErrorResponse.builder()
            .success(false)
            .errorCode(exceptionCode.getCode())
            .message(message)
            .timestamp(LocalDateTime.now())
            .build();
    }
    
    /**
     * ایجاد ErrorResponse از ErrorCode با جزئیات اضافی
     */
    public static ErrorResponse fromErrorCode(UserStatus exceptionCode, String details, MessageSource messageSource) {
        String message = messageSource.getMessage(
            exceptionCode.getMessageKey(),
            null, 
            exceptionCode.getMessageKey(), // fallback
            LocaleContextHolder.getLocale()
        );
        
        return ErrorResponse.builder()
            .success(false)
            .errorCode(exceptionCode.getCode())
            .message(message)
            .details(details)
            .timestamp(LocalDateTime.now())
            .build();
    }
    
    /**
     * ایجاد ErrorResponse از ErrorCode با مسیر
     */
    public static ErrorResponse fromErrorCode(ExceptionCode exceptionCode, String details, String path, MessageSource messageSource) {
        String message = messageSource.getMessage(
            exceptionCode.getMessageKey(),
            null, 
            exceptionCode.getMessageKey(), // fallback
            LocaleContextHolder.getLocale()
        );
        
        return ErrorResponse.builder()
            .success(false)
            .errorCode(exceptionCode.getCode())
            .message(message)
            .details(details)
            .path(path)
            .timestamp(LocalDateTime.now())
            .build();
    }
    
    /**
     * ایجاد ErrorResponse برای خطاهای اعتبارسنجی
     */
    public static ErrorResponse validationError(Map<String, Object> validationErrors, String path, MessageSource messageSource) {
        String message = messageSource.getMessage(
            UserStatus.VALIDATION_ERROR.getMessageKey(),
            null, 
            UserStatus.VALIDATION_ERROR.getMessageKey(), // fallback
            LocaleContextHolder.getLocale()
        );
        
        return ErrorResponse.builder()
            .success(false)
            .errorCode(UserStatus.VALIDATION_ERROR.getCode())
            .message(message)
            .validationErrors(validationErrors)
            .path(path)
            .timestamp(LocalDateTime.now())
            .build();
    }
    
    /**
     * ایجاد ErrorResponse با پیام سفارشی
     */
    public static ErrorResponse customError(String errorCode, String messageKey, String details, String path, MessageSource messageSource) {
        String message = messageSource.getMessage(
            messageKey, 
            null, 
            messageKey, // fallback
            LocaleContextHolder.getLocale()
        );
        
        return ErrorResponse.builder()
            .success(false)
            .errorCode(errorCode)
            .message(message)
            .details(details)
            .path(path)
            .timestamp(LocalDateTime.now())
            .build();
    }
}
