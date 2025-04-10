package com.example.trade.dto;

import com.example.trade.constant.City;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Builder
public class BusinessInputDto {

    @Schema(description = "시/도 정보", example = "전북특별자치도")
    private City city;


    @Schema(description = "군/구 정보", example = "김제시")
    private String district;
}
