package com.example.trade.exception;

public class APIDailyRequestLimitExceededException extends RuntimeException {
    public APIDailyRequestLimitExceededException(String message) {
        super(message);
    }
}