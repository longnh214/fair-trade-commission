package com.example.trade.exception;

public class FileParsingFailException extends RuntimeException{
    public FileParsingFailException(String message, Exception e){
        super(message, e);
    }
}
