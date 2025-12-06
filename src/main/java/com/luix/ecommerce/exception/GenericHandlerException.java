package com.luix.ecommerce.exception;

public class GenericHandlerException extends RuntimeException {

    public GenericHandlerException() {
        super("Something wnt wrong, please try again later.");
    }
}
