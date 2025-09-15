package com.example.san.Controller.Exception;

import com.example.san.enums.UserStatus;
import org.springframework.http.HttpStatus;

/**
 * Enum برای مدیریت کدهای خطا و کلیدهای پیام
 */

public interface ExceptionCode {


  default String getCode() {
    throw new UnsupportedOperationException();
  }

  default String getMessageKey() {
    throw new UnsupportedOperationException();
  }

  default HttpStatus getHttpStatus() {
    throw new UnsupportedOperationException();
  }

  ExceptionCode fromCode(String code);


  default boolean isAuthenticationError() {
    return getCode().startsWith("AUTH_");
  }

  default boolean isValidationError() {
    return getCode().startsWith("VAL_");
  }

  default boolean isUserError() {
    return getCode().startsWith("USER_");
  }

}
