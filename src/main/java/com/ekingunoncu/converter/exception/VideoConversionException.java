package com.ekingunoncu.converter.exception;

/**
 * This class represents an exception that can be thrown when an error occurs
 * during video conversion.
 * It extends the Exception class and provides two constructors for creating
 * exceptions with a message and/or cause.
 */
public class VideoConversionException extends Exception {

    public VideoConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoConversionException(String message) {
        super(message);
    }
}