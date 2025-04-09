package com.example.trade.exception;

public class APIResponseFormatNotMatchException extends RuntimeException {
    public APIResponseFormatNotMatchException(String message){
        super(message);
    }
}