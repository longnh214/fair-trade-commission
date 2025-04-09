package com.example.trade.exception;

import com.example.trade.constant.City;

public class InvalidDistrictException extends RuntimeException {

    public InvalidDistrictException(City city, String district) {
        super(String.format("'%s' 시/도에 '%s' 군/구는 존재하지 않습니다.", city, district));
    }

    public InvalidDistrictException(String message) {
        super(message);
    }
}