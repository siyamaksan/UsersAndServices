package com.example.san.Controller.Exception;

import com.example.san.enums.UserStatus;
import lombok.Getter;

/**
 * Exception سفارشی برای خطاهای اعتبارسنجی
 */
@Getter
public class ValidationException extends RuntimeException {
    
    private final UserStatus exceptionCode;
    private final String field;
    
    public ValidationException(UserStatus exceptionCode) {
        super(exceptionCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
        this.field = null;
    }
    
    public ValidationException(UserStatus exceptionCode, String field) {
        super(exceptionCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
        this.field = field;
    }
    
    public ValidationException(UserStatus exceptionCode, String field, String details) {
        super(exceptionCode.getMessageKey() + ": " + details); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
        this.field = field;
    }
}
