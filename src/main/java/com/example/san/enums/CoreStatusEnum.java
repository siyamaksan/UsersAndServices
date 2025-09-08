package com.example.san.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public enum CoreStatusEnum implements IStatusEnum {
  UNFILLED_NECESSARY_FIELD,
  FAILED,
  DONE;


  @Override
  public String getPrefix() {
    return "";
  }

  @Override
  public int getCode() {
    return 0;
  }

  @Override
  public boolean isSuccessStatus() {
    return false;
  }

  @Override
  public String getMessage() {
    return "";
  }
}
