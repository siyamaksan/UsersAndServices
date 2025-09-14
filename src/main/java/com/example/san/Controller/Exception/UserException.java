package com.example.san.Controller.Exception;

import lombok.Getter;

/**
 * Exception سفارشی برای خطاهای کاربر
 */
@Getter
public class UserException extends RuntimeException {
    
    private final ErrorCode errorCode;
    
    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessageKey()); // استفاده از getMessageKey به جای getMessage
        this.errorCode = errorCode;
    }
    
    public UserException(ErrorCode errorCode, String details) {
        super(errorCode.getMessageKey() + ": " + details); // استفاده از getMessageKey به جای getMessage
        this.errorCode = errorCode;
    }
    
    public UserException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessageKey(), cause); // استفاده از getMessageKey به جای getMessage
        this.errorCode = errorCode;
    }
}
