package com.example.trade.dto;

import com.univocity.parsers.annotations.Parsed;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Builder
public class FtcDataDto {
    @Parsed(field = "통신판매번호")
    private String commSaleNumber;

    @Parsed(field = "신고기관명")
    private String reportAgency;

    @Parsed(field = "상호")
    private String businessName;

    @Parsed(field = "사업자등록번호")
    private String businessNumber;

    @Parsed(field = "법인여부")
    private String isCorporate;

    @Parsed(field = "대표자명")
    private String ceoName;

    @Parsed(field = "전화번호")
    private String phone;

    @Parsed(field = "전자우편")
    private String email;

    @Parsed(field = "신고일자")
    private String reportDate;

    @Parsed(field = "사업장소재지")
    private String address;

    @Parsed(field = "사업장소재지(도로명)")
    private String roadAddress;

    @Parsed(field = "업소상태")
    private String businessStatus;

    @Parsed(field = "신고기관 대표연락처")
    private String agencyPhone;

    @Parsed(field = "판매방식")
    private String salesMethod;

    @Parsed(field = "취급품목")
    private String items;

    @Parsed(field = "인터넷도메인")
    private String domain;

    @Parsed(field = "호스트서버소재지")
    private String serverLocation;
}