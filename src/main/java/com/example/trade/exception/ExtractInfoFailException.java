package com.example.trade.exception;

public class ExtractInfoFailException extends RuntimeException{
    public ExtractInfoFailException(String message, Exception e){
        super(message, e);
    }
}
