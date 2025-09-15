package com.example.san.enums;

import com.example.san.Controller.Exception.ExceptionCode;
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
