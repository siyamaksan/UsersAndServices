package com.example.san.enums;

import com.example.san.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum GeneralStatus implements ExceptionCode {

  NOT_DEFAINED("AUTH_000", "error.server.internal.error", HttpStatus.INTERNAL_SERVER_ERROR),

  // خطاهای سرور
  INTERNAL_SERVER_ERROR("SRV_001", "error.server.internal.error", HttpStatus.INTERNAL_SERVER_ERROR),
  DATABASE_ERROR("SRV_002", "error.server.database.error", HttpStatus.INTERNAL_SERVER_ERROR),
  SERVICE_UNAVAILABLE("SRV_003", "error.server.service.unavailable", HttpStatus.SERVICE_UNAVAILABLE),

  // خطاهای عمومی
  RESOURCE_NOT_FOUND("GEN_001", "error.general.resource.not.found", HttpStatus.NOT_FOUND),
  METHOD_NOT_ALLOWED("GEN_002", "error.general.method.not.allowed", HttpStatus.METHOD_NOT_ALLOWED),
  REQUEST_TIMEOUT("GEN_003", "error.general.request.timeout", HttpStatus.REQUEST_TIMEOUT),
  TOO_MANY_REQUESTS("GEN_004", "error.general.too.many.requests", HttpStatus.TOO_MANY_REQUESTS),
// Add these to your existing UserStatus enum

  // Date/Time related errors
  INVALID_DATE_FORMAT("INVALID_DATE_FORMAT", "Invalid date/time format", HttpStatus.BAD_REQUEST),

  // Conversion errors
  CONVERSION_ERROR("CONVERSION_ERROR", "Data conversion failed", HttpStatus.BAD_REQUEST),
  INVALID_PARAMETER_TYPE("INVALID_PARAMETER_TYPE", "Invalid parameter type", HttpStatus.BAD_REQUEST),

  // Request parameter errors
  MISSING_REQUIRED_PARAMETER("MISSING_REQUIRED_PARAMETER", "Required parameter is missing", HttpStatus.BAD_REQUEST),

  // Serialization errors
  SERIALIZATION_ERROR("SERIALIZATION_ERROR", "JSON serialization error", HttpStatus.INTERNAL_SERVER_ERROR),
  MESSAGE_CONVERSION_ERROR("MESSAGE_CONVERSION_ERROR", "HTTP message conversion error", HttpStatus.BAD_REQUEST),

  // General validation errors
  INVALID_ARGUMENT("INVALID_ARGUMENT", "Invalid argument provided", HttpStatus.BAD_REQUEST),
  DUPLICATE_RECORD("GEN_005", "error.general.duplicate.record", HttpStatus.CONFLICT);

  private final String code;
  private final String messageKey;
  private final HttpStatus httpStatus;


  @Override
  public GeneralStatus fromCode(String code) {
    for (GeneralStatus exceptionCode : values()) {
      if (exceptionCode.getCode().equals(code)) {
        return exceptionCode;
      }
    }
    return NOT_DEFAINED;
  }

}
