package com.example.san.Controller.Exception;

import com.example.san.Controller.DTO.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * مدیریت سراسری خطاهای API با پشتیبانی از i18n
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * مدیریت خطاهای احراز هویت Spring Security
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleSpringAuthenticationException(
            AuthenticationException ex, WebRequest request) {
        
        ErrorCode errorCode;
        if (ex instanceof BadCredentialsException) {
            errorCode = ErrorCode.AUTHENTICATION_FAILED;
        } else if (ex instanceof DisabledException) {
            errorCode = ErrorCode.ACCOUNT_DISABLED;
        } else if (ex instanceof LockedException) {
            errorCode = ErrorCode.ACCOUNT_LOCKED;
        } else {
            errorCode = ErrorCode.AUTHENTICATION_FAILED;
        }
        
        log.error("Authentication error: {} - {}", errorCode.getCode(), ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            errorCode, 
            ex.getMessage(), 
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Method not supported");
        body.put("message", ex.getMessage());
        body.put("supportedMethods", ex.getSupportedHttpMethods());

        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(body);
    }

    /**
     * مدیریت خطاهای احراز هویت سفارشی
     */
    @ExceptionHandler(com.example.san.Controller.Exception.AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleCustomAuthenticationException(
            com.example.san.Controller.Exception.AuthenticationException ex, WebRequest request) {
        
        log.error("Custom authentication error: {} - {}", ex.getErrorCode().getCode(), ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            ex.getErrorCode(),
            ex.getMessage(),
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    /**
     * مدیریت خطاهای اعتبارسنجی
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        log.error("Validation error: {}", errors);
        
        ErrorResponse errorResponse = ErrorResponse.validationError(
            errors, 
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * مدیریت خطاهای اعتبارسنجی سفارشی
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleCustomValidationException(
            ValidationException ex, WebRequest request) {
        
        log.error("Custom validation error: {} - {}", ex.getErrorCode().getCode(), ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            ex.getErrorCode(),
            ex.getMessage(),
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    /**
     * مدیریت خطاهای کاربر سفارشی
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(
            UserException ex, WebRequest request) {
        
        log.error("User error: {} - {}", ex.getErrorCode().getCode(), ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            ex.getErrorCode(),
            ex.getMessage(),
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    /**
     * مدیریت خطاهای IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        log.error("Argument error: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            ErrorCode.VALIDATION_ERROR,
            ex.getMessage(),
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * مدیریت خطاهای عمومی
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            ErrorCode.INTERNAL_SERVER_ERROR,
            "خطای داخلی سرور",
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(errorResponse);
    }
}
