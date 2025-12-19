package com.luix.ecommerce.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardExceptionMessage> accessDenied(AccessDeniedException e, HttpServletRequest request) {
        String error = "You don't have the permission to execute this action.";
        HttpStatus status = HttpStatus.FORBIDDEN;

        StandardExceptionMessage err = new StandardExceptionMessage(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardExceptionMessage> badCredentials(BadCredentialsException e, HttpServletRequest request) {
        String error = "Incorrect e-mail or password.";
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        StandardExceptionMessage err = new StandardExceptionMessage(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardExceptionMessage> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found.";
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardExceptionMessage err = new StandardExceptionMessage(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DuplicatedItemException.class)
    public ResponseEntity<StandardExceptionMessage> duplicatedItem(DuplicatedItemException e, HttpServletRequest request) {
        String error = "Order contains duplicate products";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardExceptionMessage err = new StandardExceptionMessage(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(StockInsufficientException.class)
    public ResponseEntity<StandardExceptionMessage> stockInsufficient(StockInsufficientException e, HttpServletRequest request) {
        String error = "This item is out of stock.";
        HttpStatus status = HttpStatus.CONFLICT;

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
