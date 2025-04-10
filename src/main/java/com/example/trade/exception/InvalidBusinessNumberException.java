package com.example.trade.exception;

public class InvalidBusinessNumberException extends RuntimeException{
    public InvalidBusinessNumberException(String message){
        super(message);
    }
}
