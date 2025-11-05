package com.storehub.garage.admin.exception;

public class ApiConnectivityException extends RuntimeException {
    public ApiConnectivityException(String message, Throwable cause) {
        super(message, cause);
    }
}
