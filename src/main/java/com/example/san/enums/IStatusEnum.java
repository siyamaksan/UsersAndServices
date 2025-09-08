package com.example.san.enums;

public interface IStatusEnum {
  String getPrefix();

  int getCode();

  boolean isSuccessStatus();

  String getMessage();


  public default String getMessage(Object... params) {
    return String.format(getMessage(), params);
  }

}
