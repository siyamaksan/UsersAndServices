package com.example.san.Controller.Exception;

import lombok.Getter;

/**
 * Exception سفارشی برای خطاهای اعتبارسنجی
 */
@Getter
public class ValidationException extends RuntimeException {
    
    private final ExceptionCode exceptionCode;
    private final String field;
    
    public ValidationException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
        this.field = null;
    }
    
    public ValidationException(ExceptionCode exceptionCode, String field) {
        super(exceptionCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
        this.field = field;
    }
    
    public ValidationException(ExceptionCode exceptionCode, String field, String details) {
        super(exceptionCode.getMessageKey() + ": " + details); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
        this.field = field;
    }
}
