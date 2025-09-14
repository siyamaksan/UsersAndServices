package com.example.san.Controller.Exception;

import lombok.Getter;

/**
 * Exception سفارشی برای خطاهای احراز هویت
 */
@Getter
public class AuthenticationException extends RuntimeException {
    
    private final ErrorCode errorCode;
    
    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.errorCode = errorCode;
    }
    
    public AuthenticationException(ErrorCode errorCode, String details) {
        super(errorCode.getMessageKey() + ": " + details); // استفاده از getMessageKey به جای getMessage
        this.errorCode = errorCode;
    }
    
    public AuthenticationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessageKey(), cause); // استفاده از getMessageKey به جای getMessage
        this.errorCode = errorCode;
    }
}
