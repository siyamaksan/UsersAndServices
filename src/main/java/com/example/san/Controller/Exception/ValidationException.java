package com.example.san.Controller.Exception;

import lombok.Getter;

/**
 * Exception سفارشی برای خطاهای اعتبارسنجی
 */
@Getter
public class ValidationException extends RuntimeException {
    
    private final ErrorCode errorCode;
    private final String field;
    
    public ValidationException(ErrorCode errorCode) {
        super(errorCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.errorCode = errorCode;
        this.field = null;
    }
    
    public ValidationException(ErrorCode errorCode, String field) {
        super(errorCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.errorCode = errorCode;
        this.field = field;
    }
    
    public ValidationException(ErrorCode errorCode, String field, String details) {
        super(errorCode.getMessageKey() + ": " + details); // استفاده از getMessageKey به جای getMessage
        this.errorCode = errorCode;
        this.field = field;
    }
}
