package com.mobiquity.exception;

/**
 * Exception thrown for any undesired behaviour while processing
 * delivery packages.
 *
 * @author thato
 */
public class APIException extends RuntimeException {

  /**
   * Constructor which takes the exception message and
   * the exception type.
   *
   * @param message String message
   * @param e Exception type
   */
  public APIException(String message, Exception e) {
    super(message, e);
  }

  /**
   * Constructor which takes the exception message.
   *
   * @param message String message
   */
  public APIException(String message) {
    super(message);
  }
}
