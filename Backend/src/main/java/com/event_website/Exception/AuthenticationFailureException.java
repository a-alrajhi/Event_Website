package com.event_website.Exception;

/**
 * @author Omar Alomair
 *
 * This is the exception called if the auth process fails for any reason, or if the service is authenticated and the user is not (401 response).
 *
 * */
public class AuthenticationFailureException extends RuntimeException {
    public AuthenticationFailureException(String message) {
        super(message);
    }
}
