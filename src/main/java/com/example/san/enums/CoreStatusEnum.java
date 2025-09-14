package com.example.san.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CoreStatusEnum implements IStatusEnum {
  UNFILLED_NECESSARY_FIELD,
  FAILED,
  DONE(true),
  NOT_FOUND(false);

  private final boolean isSuccessStatus;
  private String localizedMessage = null;


  CoreStatusEnum() {
    this(true);
  }

  private CoreStatusEnum( boolean isSuccessStatus) {
    this.isSuccessStatus = isSuccessStatus;
  }


  @Override
  public String getPrefix() {
    return "CS";
  }

  @Override
  public int getCode() {
    return 0;
  }

  @Override
  public boolean isSuccessStatus() {
    return isSuccessStatus;
  }

  @Override
  public String getMessage() {
    return "";
  }


}
