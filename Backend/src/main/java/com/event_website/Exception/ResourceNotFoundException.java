package com.event_website.Exception;

/**
 * @author Omar Alomair
 *
 * Throw this exception if the request ends up not finding the needed resource (404 response)
 *
 * */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
