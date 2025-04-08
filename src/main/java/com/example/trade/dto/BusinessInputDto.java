package com.example.trade.dto;

import com.example.trade.constant.City;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusinessInputData {
    private City city;
    private String district;
}
