package com.example.san.enums;

import com.example.san.Controller.Exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserStatus implements ExceptionCode {
  NOT_DEFAINED("AUTH_000", "error.server.internal.error", HttpStatus.INTERNAL_SERVER_ERROR),

  // خطاهای احراز هویت
  AUTHENTICATION_FAILED("AUTH_001", "error.auth.authentication.failed", HttpStatus.UNAUTHORIZED),
  ACCOUNT_DISABLED("AUTH_002", "error.auth.account.disabled", HttpStatus.UNAUTHORIZED),
  ACCOUNT_LOCKED("AUTH_003", "error.auth.account.locked", HttpStatus.UNAUTHORIZED),
  TOKEN_EXPIRED("AUTH_004", "error.auth.token.expired", HttpStatus.UNAUTHORIZED),
  TOKEN_INVALID("AUTH_005", "error.auth.token.invalid", HttpStatus.UNAUTHORIZED),
  TOKEN_MISSING("AUTH_006", "error.auth.token.missing", HttpStatus.UNAUTHORIZED),

  // خطاهای مجوز
  ACCESS_DENIED("AUTH_007", "error.auth.access.denied", HttpStatus.FORBIDDEN),
  INSUFFICIENT_PERMISSIONS("AUTH_008", "error.auth.insufficient.permissions", HttpStatus.FORBIDDEN),

  // خطاهای اعتبارسنجی
  VALIDATION_ERROR("VAL_001", "error.validation.validation.error", HttpStatus.BAD_REQUEST),
  REQUIRED_FIELD_MISSING("VAL_002", "error.validation.required.field.missing", HttpStatus.BAD_REQUEST),
  INVALID_FORMAT("VAL_003", "error.validation.invalid.format", HttpStatus.BAD_REQUEST),
  PASSWORD_TOO_WEAK("VAL_004", "error.validation.password.too.weak", HttpStatus.BAD_REQUEST),
  USERNAME_ALREADY_EXISTS("VAL_005", "error.validation.username.already.exists", HttpStatus.BAD_REQUEST),

  // خطاهای کاربر
  USER_NOT_FOUND("USER_001", "error.user.not.found", HttpStatus.NOT_FOUND),
  USER_ALREADY_EXISTS("USER_002", "error.user.already.exists", HttpStatus.CONFLICT),
  USER_INACTIVE("USER_003", "error.user.inactive", HttpStatus.BAD_REQUEST),


INSUFFICIENT_INVENTORY("GEN_004", "error.general.too.many.requests", HttpStatus.CONFLICT);

  private final String code;
  private final String messageKey;
  private final HttpStatus httpStatus;


  @Override
  public UserStatus fromCode(String code) {
        for (UserStatus exceptionCode : values()) {
            if (exceptionCode.getCode().equals(code)) {
                return exceptionCode;
            }
        }
        return NOT_DEFAINED;
  }


}
