package com.toosterr.apigateway.exception.user;

public class UnauthorizedUser extends RuntimeException {
    public UnauthorizedUser(String message) {
        super(message);
    }
}
