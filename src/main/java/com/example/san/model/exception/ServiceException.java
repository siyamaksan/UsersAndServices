package com.example.san.model.exception;

// Custom exception for service layer
public class ServiceException extends RuntimeException {
  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
