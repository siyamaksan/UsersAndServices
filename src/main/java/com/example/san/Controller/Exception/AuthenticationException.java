package com.example.san.Controller.Exception;

import lombok.Getter;

/**
 * Exception سفارشی برای خطاهای احراز هویت
 */
@Getter
public class AuthenticationException extends RuntimeException {
    
    private final ExceptionCode exceptionCode;
    
    public AuthenticationException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
    
    public AuthenticationException(ExceptionCode exceptionCode, String details) {
        super(exceptionCode.getMessageKey() + ": " + details); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
    
    public AuthenticationException(ExceptionCode exceptionCode, Throwable cause) {
        super(exceptionCode.getMessageKey(), cause); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
}
