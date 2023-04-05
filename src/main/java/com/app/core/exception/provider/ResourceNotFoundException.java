package com.app.core.exception.provider;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String id) {
        super("NoContentException, area with " + id + " not found.");
    }
}
