package com.example.san.Controller.Exception;

import com.example.san.enums.UserStatus;
import lombok.Getter;

/**
 * Exception سفارشی برای خطاهای کاربر
 */
@Getter
public class UserException extends RuntimeException {
    
    private final UserStatus exceptionCode;
    
    public UserException(UserStatus exceptionCode) {
        super(exceptionCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
    
    public UserException(UserStatus exceptionCode, String details) {
        super(exceptionCode.getMessageKey() + ": " + details); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
    
    public UserException(UserStatus exceptionCode, Throwable cause) {
        super(exceptionCode.getMessageKey(), cause); // استفاده از getMessageKey به جای getMessage
        this.exceptionCode = exceptionCode;
    }
}
