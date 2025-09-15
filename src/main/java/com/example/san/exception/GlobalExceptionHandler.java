package com.example.san.exception;

import com.example.san.model.dto.ErrorResponse;
import com.example.san.enums.GeneralStatus;
import com.example.san.enums.UserStatus;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
        
        UserStatus exceptionCode;
        if (ex instanceof BadCredentialsException) {
            exceptionCode = UserStatus.AUTHENTICATION_FAILED;
        } else if (ex instanceof DisabledException) {
            exceptionCode = UserStatus.ACCOUNT_DISABLED;
        } else if (ex instanceof LockedException) {
            exceptionCode = UserStatus.ACCOUNT_LOCKED;
        } else {
            exceptionCode = UserStatus.AUTHENTICATION_FAILED;
        }
        
        log.error("Authentication error: {} - {}", exceptionCode.getCode(), ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            exceptionCode,
            ex.getMessage(), 
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.status(exceptionCode.getHttpStatus()).body(errorResponse);
    }
    /**
     * مدیریت خطاهای یکتایی دیتابیس (Unique Constraint Violation)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleUniqueConstraintViolation(
        DataIntegrityViolationException ex, WebRequest request) {

        GeneralStatus exceptionCode = GeneralStatus.DUPLICATE_RECORD;

        log.error("Unique constraint violation: {} - {}", exceptionCode.getCode(), ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            exceptionCode,
            "This user already has this service.",
            request.getDescription(false),
            messageSource
        );

        return ResponseEntity.status(exceptionCode.getHttpStatus()).body(errorResponse);
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
    @ExceptionHandler(com.example.san.exception.AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleCustomAuthenticationException(
            com.example.san.exception.AuthenticationException ex, WebRequest request) {
        
        log.error("Custom authentication error: {} - {}", ex.getExceptionCode().getCode(), ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            ex.getExceptionCode(),
            ex.getMessage(),
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.status(ex.getExceptionCode().getHttpStatus()).body(errorResponse);
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
        
        log.error("Custom validation error: {} - {}", ex.getExceptionCode().getCode(), ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            ex.getExceptionCode(),
            ex.getMessage(),
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.status(ex.getExceptionCode().getHttpStatus()).body(errorResponse);
    }

    /**
     * مدیریت خطاهای کاربر سفارشی
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(
            UserException ex, WebRequest request) {
        
        log.error("User error: {} - {}", ex.getExceptionCode().getCode(), ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            ex.getExceptionCode(),
            ex.getMessage(),
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.status(ex.getExceptionCode().getHttpStatus()).body(errorResponse);
    }

    /**
     * مدیریت خطاهای عمومی
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            GeneralStatus.INTERNAL_SERVER_ERROR,
            "خطای داخلی سرور",
            request.getDescription(false),
            messageSource
        );
        
        return ResponseEntity.status(GeneralStatus.INTERNAL_SERVER_ERROR.getHttpStatus()).body(errorResponse);
    }

    // Handle DateTime parsing errors
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeParseException(
        DateTimeParseException ex, WebRequest request) {

        log.error("DateTime parsing error: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            GeneralStatus.INVALID_DATE_FORMAT, // You'll need to define this in UserStatus
            "Invalid date/time format. Expected format: yyyy-MM-ddTHH:mm:ss",
            request.getDescription(false),
            messageSource
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle conversion failures (like String to LocalDateTime)
    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ErrorResponse> handleConversionFailedException(
        ConversionFailedException ex, WebRequest request) {

        log.error("Conversion failed: {} - {}", ex.getTargetType(), ex.getMessage());

        String targetType = ex.getTargetType().getName();
        String message = String.format("Failed to convert value to %s. Please check the format.", targetType);

        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            GeneralStatus.CONVERSION_ERROR, // You'll need to define this
            message,
            request.getDescription(false),
            messageSource
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle method argument type mismatch
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException ex, WebRequest request) {

        log.error("Method argument type mismatch: parameter '{}', required type: {}, provided value: '{}'",
            ex.getName(), ex.getRequiredType().getSimpleName(), ex.getValue());

        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
            ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName());

        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            GeneralStatus.INVALID_PARAMETER_TYPE, // You'll need to define this
            message,
            request.getDescription(false),
            messageSource
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle missing request parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException ex, WebRequest request) {

        log.error("Missing required parameter: '{}' of type {}", ex.getParameterName(), ex.getParameterType());

        String message = String.format("Required parameter '%s' of type %s is missing",
            ex.getParameterName(), ex.getParameterType());

        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            GeneralStatus.MISSING_REQUIRED_PARAMETER, // You'll need to define this
            message,
            request.getDescription(false),
            messageSource
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle Jackson serialization errors
    @ExceptionHandler(InvalidDefinitionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDefinitionException(
        InvalidDefinitionException ex, WebRequest request) {

        log.error("JSON serialization error: {}", ex.getMessage());

        String message = "Error in JSON serialization. Please check the response data structure.";

        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            GeneralStatus.SERIALIZATION_ERROR, // You'll need to define this
            message,
            request.getDescription(false),
            messageSource
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Handle HTTP message conversion errors
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageConversionException(
        HttpMessageConversionException ex, WebRequest request) {

        log.error("HTTP message conversion error: {}", ex.getMessage());

        String message = "Error converting HTTP message. Please check request/response format.";

        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            GeneralStatus.MESSAGE_CONVERSION_ERROR, // You'll need to define this
            message,
            request.getDescription(false),
            messageSource
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Generic handler for IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
        IllegalArgumentException ex, WebRequest request) {

        log.error("Illegal argument: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(
            GeneralStatus.INVALID_ARGUMENT, // You'll need to define this
            ex.getMessage(),
            request.getDescription(false),
            messageSource
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
