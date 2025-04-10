package com.example.trade.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "corporate_info")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorporateInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "corporateInfo_seq_gen")
    @SequenceGenerator(name = "corporateInfo_seq_gen", sequenceName = "corporateInfo_seq", allocationSize = 100)
    private Long id;

    @Schema(name = "통신판매번호")
    private String commSaleNumber;

    @Schema(name = "상호")
    private String businessName;

    @Schema(name = "사업자등록번호")
    private String businessNumber;

    @Schema(name = "법인등록번호")
    private String corporateNumber;

    @Schema(name = "행정구역코드")
    private String districtCode;
}