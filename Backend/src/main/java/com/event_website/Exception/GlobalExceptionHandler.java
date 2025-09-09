package com.event_website.Exception;

import com.event_website.Dto.ErrorDTO;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

/**
 * @author Omar Alomair
 *
 * This class handles exceptions for the application. It returns the appropriate response using the ErrorResponse DTO.
 * make new functions that return a ResponseEntity of ErrorResponse for any exceptions that need custom handling
 * For your class to work with this handler just throw the exception when needed.
 *
 * */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle custom 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    // Handle invalid JSON or request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleInvalidBody(HttpMessageNotReadableException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    // Handle validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                new ErrorDTO(HttpStatus.BAD_REQUEST.value(), message, ZonedDateTime.now(), request.getDescription(false)),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle constraint violations (e.g., @RequestParam, @PathVariable)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    // Handle authentication failures (e.g., bad credentials, email already taken)
    @ExceptionHandler(AuthenticationFailureException.class)
    public ResponseEntity<ErrorDTO> handleAuthenticationFailure(AuthenticationFailureException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, request);
    }

    // Catch-all for unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneral(Exception ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // Utility method to build a response
    private ResponseEntity<ErrorDTO> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        ErrorDTO error = new ErrorDTO(
                status.value(),
                ex.getMessage(),
                ZonedDateTime.now(),
                request.getDescription(false)
        );
        logger.error("Error occurred:", ex);
        return new ResponseEntity<>(error, status);
    }
}

