package com.example.san.exception;

import com.example.san.enums.GeneralStatus;

public class GeneralException extends RuntimeException {

  private final GeneralStatus status;

  public GeneralException(GeneralStatus status) {
    super(status.getMessageKey()); // استفاده از getMessageKey به جای getMessage
    this.status = status;
  }

  public GeneralException(GeneralStatus status, String details) {
    super(status.getMessageKey() + ": " + details); // استفاده از getMessageKey به جای getMessage
    this.status = status;
  }

  public GeneralException(GeneralStatus status, Throwable cause) {
    super(status.getMessageKey(), cause); // استفاده از getMessageKey به جای getMessage
    this.status = status;
  }

}
