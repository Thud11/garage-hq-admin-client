package com.storehub.garage.admin.exception;

public class ApiAuthorizationException extends RuntimeException {
    public ApiAuthorizationException(String message) {
        super(message);
    }
}
