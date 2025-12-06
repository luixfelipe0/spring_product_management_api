package com.luix.ecommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardExceptionMessage> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found.";
        HttpStatus status = HttpStatus.NOT_FOUND;

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
