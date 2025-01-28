package com.example.feignclient.remote;

public class RetryException extends RuntimeException {
  public RetryException(String message) {
    super(message);
  }
}
