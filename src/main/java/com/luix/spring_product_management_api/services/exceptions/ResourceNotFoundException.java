package com.luix.spring_product_management_api.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        super("Resource not found. Id: " + id);
    }
}
