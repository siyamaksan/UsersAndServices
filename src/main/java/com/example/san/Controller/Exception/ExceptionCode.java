package com.example.san.Controller.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum برای مدیریت کدهای خطا و کلیدهای پیام
 */
@Getter
public enum ExceptionCode {
    
    // خطاهای احراز هویت
    AUTHENTICATION_FAILED("AUTH_001", "error.auth.authentication.failed", HttpStatus.UNAUTHORIZED),
    ACCOUNT_DISABLED("AUTH_002", "error.auth.account.disabled", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED("AUTH_003", "error.auth.account.locked", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("AUTH_004", "error.auth.token.expired", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID("AUTH_005", "error.auth.token.invalid", HttpStatus.UNAUTHORIZED),
    TOKEN_MISSING("AUTH_006", "error.auth.token.missing", HttpStatus.UNAUTHORIZED),
    
    // خطاهای مجوز
    ACCESS_DENIED("AUTH_007", "error.auth.access.denied", HttpStatus.FORBIDDEN),
    INSUFFICIENT_PERMISSIONS("AUTH_008", "error.auth.insufficient.permissions", HttpStatus.FORBIDDEN),
    
    // خطاهای اعتبارسنجی
    VALIDATION_ERROR("VAL_001", "error.validation.validation.error", HttpStatus.BAD_REQUEST),
    REQUIRED_FIELD_MISSING("VAL_002", "error.validation.required.field.missing", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT("VAL_003", "error.validation.invalid.format", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_WEAK("VAL_004", "error.validation.password.too.weak", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS("VAL_005", "error.validation.username.already.exists", HttpStatus.BAD_REQUEST),
    
    // خطاهای کاربر
    USER_NOT_FOUND("USER_001", "error.user.not.found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("USER_002", "error.user.already.exists", HttpStatus.CONFLICT),
    USER_INACTIVE("USER_003", "error.user.inactive", HttpStatus.BAD_REQUEST),
    
    // خطاهای سرور
    INTERNAL_SERVER_ERROR("SRV_001", "error.server.internal.error", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_ERROR("SRV_002", "error.server.database.error", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE("SRV_003", "error.server.service.unavailable", HttpStatus.SERVICE_UNAVAILABLE),
    
    // خطاهای عمومی
    RESOURCE_NOT_FOUND("GEN_001", "error.general.resource.not.found", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED("GEN_002", "error.general.method.not.allowed", HttpStatus.METHOD_NOT_ALLOWED),
    REQUEST_TIMEOUT("GEN_003", "error.general.request.timeout", HttpStatus.REQUEST_TIMEOUT),
    TOO_MANY_REQUESTS("GEN_004", "error.general.too.many.requests", HttpStatus.TOO_MANY_REQUESTS);
    
    private final String code;
    private final String messageKey;
    private final HttpStatus httpStatus;
    
    ExceptionCode(String code, String messageKey, HttpStatus httpStatus) {
        this.code = code;
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
    }
    
    /**
     * دریافت ErrorCode بر اساس کد
     */
    public static ExceptionCode fromCode(String code) {
        for (ExceptionCode exceptionCode : values()) {
            if (exceptionCode.getCode().equals(code)) {
                return exceptionCode;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }
    
    /**
     * بررسی اینکه آیا خطا مربوط به احراز هویت است یا نه
     */
    public boolean isAuthenticationError() {
        return this.code.startsWith("AUTH_");
    }
    
    /**
     * بررسی اینکه آیا خطا مربوط به اعتبارسنجی است یا نه
     */
    public boolean isValidationError() {
        return this.code.startsWith("VAL_");
    }
    
    /**
     * بررسی اینکه آیا خطا مربوط به کاربر است یا نه
     */
    public boolean isUserError() {
        return this.code.startsWith("USER_");
    }
}
