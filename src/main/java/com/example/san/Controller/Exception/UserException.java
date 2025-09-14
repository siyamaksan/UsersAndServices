package com.example.san.Controller.Exception;

import lombok.Getter;

/**
 * Exception سفارشی برای خطاهای کاربر
 */
@Getter
public class UserException extends RuntimeException {
    
    private final ExceptionCode exceptionCode;
    
    public UserException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
    
    public UserException(ExceptionCode exceptionCode, String details) {
        super(exceptionCode.getMessageKey() + ": " + details); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
    
    public UserException(ExceptionCode exceptionCode, Throwable cause) {
        super(exceptionCode.getMessageKey(), cause); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
}
