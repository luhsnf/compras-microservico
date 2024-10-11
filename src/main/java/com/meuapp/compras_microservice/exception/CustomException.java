package com.meuapp.compras_microservice.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
