package com.luix.ecommerce.exception;

public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message) {
        super("Invalid credentials");
    }
}
