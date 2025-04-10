package com.example.trade.exception;

public class InvalidCityNameException extends RuntimeException{
    public InvalidCityNameException(String message){
        super(message);
    }
}