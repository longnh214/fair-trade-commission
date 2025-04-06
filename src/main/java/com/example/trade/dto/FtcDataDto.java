package com.example.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

/**
 * 통신판매사업자 CSV 데이터 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FtcDataDto {

    /** 통신판매번호 */
    private String registrationNumber;

    /** 신고기관명 */
    private String reportAgency;

    /** 상호 (사업자명) */
    private String businessName;

    /** 사업자등록번호 */
    private String businessId;

    /** 법인여부 */
    private String isCorporation;

    /** 대표자명 */
    private String ceoName;

    /** 전화번호 */
    private String phone;

    /** 전자우편 */
    private String email;

    /** 신고일자 */
    private String reportDate;

    /** 사업장소재지 */
    private String location;

    /** 사업장소재지 (도로명) */
    private String roadLocation;

    /** 업소상태 */
    private String businessStatus;

    /** 신고기관 대표연락처 */
    private String agencyPhone;

    /** 판매방식 */
    private String salesType;

    /** 취급품목 */
    private String items;

    /** 인터넷도메인 */
    private String domain;

    /** 호스트서버소재지 */
    private String hostLocation;

    public FtcDataDto mapToDto(CSVRecord record) {
        String registrationNumber = record.get("통신판매번호");
        String reportAgency = record.get("신고기관명");
        String businessName = record.get("상호");
        String businessId = record.get("사업자등록번호");
        String isCorporation = record.get("법인여부");
        String ceoName = record.get("대표자명");
        String phone = record.get("전화번호");
        String email = record.get("전자우편");
        String reportDate = record.get("신고일자");
        String location = record.get("사업장소재지");
        String roadLocation = record.get("사업장소재지(도로명)");
        String businessStatus = record.get("업소상태");
        String agencyPhone = record.get("신고기관 대표연락처");
        String salesType = record.get("판매방식");
        String items = record.get("취급품목");
        String domain = record.get("인터넷도메인");
        String hostLocation = record.get("호스트서버소재지");

        return new FtcDataDto(
                registrationNumber,
                reportAgency,
                businessName,
                businessId,
                isCorporation,
                ceoName,
                phone,
                email,
                reportDate,
                location,
                roadLocation,
                businessStatus,
                agencyPhone,
                salesType,
                items,
                domain,
                hostLocation
        );
    }
}