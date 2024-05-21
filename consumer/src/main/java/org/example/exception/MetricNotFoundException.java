package org.example.exception;

public class MetricNotFoundException extends RuntimeException {

  public MetricNotFoundException() {
    super();
  }

  public MetricNotFoundException(String message) {
    super(message);
  }

  public MetricNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}

