package com.storehub.garage.admin.exception;

import lombok.Getter;

@Getter
public class ApiServerException extends RuntimeException {
    private final int statusCode;
    private  final String messageBody;

    public ApiServerException(String message, int statusCode, String messageBody) {
        super(message);
        this.statusCode = statusCode;
        this.messageBody = messageBody;
    }

}
