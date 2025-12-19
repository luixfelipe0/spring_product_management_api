package com.luix.ecommerce.exception;

public class DuplicatedItemException extends RuntimeException {
    public DuplicatedItemException(String message) {
        super(message);
    }
}
