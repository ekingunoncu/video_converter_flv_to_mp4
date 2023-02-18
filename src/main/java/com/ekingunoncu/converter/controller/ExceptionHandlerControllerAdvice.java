package com.ekingunoncu.converter.controller;

import java.io.FileNotFoundException;
import java.util.concurrent.RejectedExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ekingunoncu.converter.exception.VideoConversionException;

/**
 * Controller advice class for handling exceptions thrown by the application.
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    /**
     * Handles all exceptions that are not explicitly caught by other methods in
     * this class.
     *
     * @param ex the exception that was thrown
     * @return a response entity with a 500 Internal Server Error status code and an
     *         error message
     */
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<String> handleException(Exception ex) {
        LOGGER.error("Internal server error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error. Please try again later.");
    }

    /**
     * Handles exceptions thrown during video conversion.
     *
     * @param ex the exception that was thrown
     * @return a response entity with a 400 Bad Request status code and an error
     *         message
     */
    @ExceptionHandler(value = { VideoConversionException.class })
    public ResponseEntity<String> handleVideoConversionException(VideoConversionException ex) {
        LOGGER.error("Video conversion error", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Video conversion error: " + ex.getMessage());
    }

    /**
     * Handles exceptions thrown when an invalid argument is provided.
     *
     * @param ex the exception that was thrown
     * @return a response entity with a 400 Bad Request status code and an error
     *         message
     */
    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.error("Invalid argument", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid argument: " + ex.getMessage());
    }

    /**
     * Handles exceptions thrown when a file is not found.
     *
     * @param ex the exception that was thrown
     * @return a response entity with a 404 Not Found status code and an error
     *         message
     */
    @ExceptionHandler(value = { FileNotFoundException.class })
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex) {
        LOGGER.error("File not found", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + ex.getMessage());
    }

    /**
     * Handles exceptions thrown when a conversion request is rejected due to the
     * thread pool being full.
     *
     * @param ex the exception that was thrown
     * @return a response entity with a 429 Too Many Requests status code and an
     *         error message
     */
    @ExceptionHandler(value = { RejectedExecutionException.class })
    public ResponseEntity<String> handleRejectedExecutionException(RejectedExecutionException ex) {
        LOGGER.error("Conversion request rejected", ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Conversion request rejected. Please try again later.");
    }
}
