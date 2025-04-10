package com.example.trade.exception;

public class FileDownloadFailException extends RuntimeException{
    public FileDownloadFailException(String message){
        super(message);
    }
}