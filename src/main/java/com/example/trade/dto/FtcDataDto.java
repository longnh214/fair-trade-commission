package com.example.trade.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FtcDataDto {

    @CsvBindByName(column = "통신판매번호")
    private String commSaleNumber;

    @CsvBindByName(column = "상호")
    private String businessName;

    @CsvBindByName(column = "사업자등록번호")
    private String businessNumber;

    @CsvBindByName(column = "법인여부")
    private String isCorporate;

    @CsvBindByName(column = "사업장소재지")
    private String address;

    @CsvBindByName(column = "사업장소재지(도로명)")
    private String roadAddress;
}