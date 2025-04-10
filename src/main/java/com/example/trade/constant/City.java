package com.example.trade.constant;

import com.example.trade.exception.InvalidCityNameException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(description = "시/도")
public enum City {
    SEOUL("서울특별시"),
    BUSAN("부산광역시"),
    DAEGU("대구광역시"),
    INCHEON("인천광역시"),
    GWANGJU("광주광역시"),
    DAEJEON("대전광역시"),
    ULSAN("울산광역시"),
    SEJONG("세종특별자치시"),
    GYEONGGI("경기도"),
    GANGWON("강원특별자치도"),
    CHUNGBUK("충청북도"),
    CHUNGNAM("충청남도"),
    JEONBUK("전북특별자치도"),
    JEONNAM("전라남도"),
    GYEONGBUK("경상북도"),
    GYEONGNAM("경상남도"),
    JEJU("제주특별자치도"),
    FOREIGN("국외사업자");

    private final String korName;

    City(String korName) {
        this.korName = korName;
    }

    @JsonValue
    public String getKorName() {
        return korName;
    }

    @JsonCreator
    public static City from(String name) {
        return Arrays.stream(values())
                .filter(c -> c.korName.equals(name))
                .findFirst()
                .orElseThrow(() -> new InvalidCityNameException("해당 시/도 정보는 유효하지 않습니다. -> " + name));
    }
}