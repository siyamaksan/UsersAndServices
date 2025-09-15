package com.example.san.Controller.Exception;

import com.example.san.enums.UserStatus;
import lombok.Getter;

/**
 * Exception سفارشی برای خطاهای احراز هویت
 */
@Getter
public class AuthenticationException extends RuntimeException {
    
    private final UserStatus exceptionCode;
    
    public AuthenticationException(UserStatus exceptionCode) {
        super(exceptionCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
    
    public AuthenticationException(UserStatus exceptionCode, String details) {
        super(exceptionCode.getMessageKey() + ": " + details); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
    
    public AuthenticationException(UserStatus exceptionCode, Throwable cause) {
        super(exceptionCode.getMessageKey(), cause); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
}
