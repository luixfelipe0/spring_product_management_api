package com.luix.ecommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardExceptionMessage> resourceNotFoundHandler(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found.";
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardExceptionMessage err = new StandardExceptionMessage(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<StandardExceptionMessage> validationHandler(RequestValidationException e, HttpServletRequest request) {
        String error = "Validation error.";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardExceptionMessage err = new StandardExceptionMessage(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardExceptionMessage> validationHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        String error = "Validation error.";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardExceptionMessage err = new StandardExceptionMessage(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(GenericHandlerException.class)
    public ResponseEntity<StandardExceptionMessage> genericHandler(GenericHandlerException e, HttpServletRequest request) {
        String error = "Internal server error.";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardExceptionMessage err = new StandardExceptionMessage(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
