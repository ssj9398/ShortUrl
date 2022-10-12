package com.example.shorturl.advice.exception;

public class ApiRequestException extends RuntimeException{
    public ApiRequestException(String message) {
        super(message);
    }
}