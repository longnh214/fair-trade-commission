package com.example.trade.service;

import com.example.trade.feignClient.PublicDataClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PublicDataServiceTest {
    @Mock
    private PublicDataClient publicDataClient;

    @InjectMocks
    private PublicDataService publicDataService;

    Map object;

    String brno;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        String json = "{\n" +
                "  \"resultCode\": \"00\",\n" +
                "  \"resultMsg\": \"NORMAL SERVICE\",\n" +
                "  \"numOfRows\": \"1\",\n" +
                "  \"pageNo\": \"1\",\n" +
                "  \"totalCount\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"opnSn\": \"874524895969799992\",\n" +
                "      \"prmmiYr\": \"2025\",\n" +
                "      \"prmmiMnno\": \"2025-전북장수-0007\",\n" +
                "      \"ctpvNm\": \"전북특별자치도\",\n" +
                "      \"dclrInstNm\": \"N/A\",\n" +
                "      \"operSttusCdNm\": \"정상영업\",\n" +
                "      \"smtxTrgtYnCn\": \"비대상\",\n" +
                "      \"corpYnNm\": \"법인\",\n" +
                "      \"bzmnNm\": \"주식회사락앤런\",\n" +
                "      \"crno\": \"2143110000719\",\n" +
                "      \"brno\": \"1568103314\",\n" +
                "      \"telno\": \"010-개인정보\",\n" +
                "      \"fxno\": \"N/A\",\n" +
                "      \"lctnRnAddr\": \"전북특별자치도 장수군 장수읍 노하2길\",\n" +
                "      \"lctnAddr\": \"전북특별자치도 장수군 장수읍 노하리 494 번지2 호 산 1 \",\n" +
                "      \"domnCn\": \"N/A\",\n" +
                "      \"opnServerPlaceAladr\": \"N/A\",\n" +
                "      \"ntslMthdNm\": \"02\",\n" +
                "      \"ntslMthdCn\": \"인터넷\",\n" +
                "      \"trtmntPrdlstNm\": \"06\",\n" +
                "      \"ntslPrdlstCn\": \"의류/패션/잡화/뷰티\",\n" +
                "      \"dclrCn\": null,\n" +
                "      \"chgCn\": \" \",\n" +
                "      \"chgRsnCn\": \" \",\n" +
                "      \"tcbizBgngDate\": \"N/A\",\n" +
                "      \"tcbizEndDate\": \"N/A\",\n" +
                "      \"clsbizDate\": \"N/A\",\n" +
                "      \"bsnResmptDate\": \"N/A\",\n" +
                "      \"spcssRsnCn\": \" \",\n" +
                "      \"dclrDate\": \"20250217\",\n" +
                "      \"lctnRnOzip\": \"55631\",\n" +
                "      \"rnAddr\": \"전북특별자치도 장수군 장수읍 노하2길\",\n" +
                "      \"opnMdfcnDt\": \"20250217000000\",\n" +
                "      \"prcsDeptDtlNm\": \"N/A\",\n" +
                "      \"prcsDeptAreaNm\": \" \",\n" +
                "      \"prcsDeptNm\": \"N/A\",\n" +
                "      \"chrgDeptTelno\": \"N/A\",\n" +
                "      \"rprsvNm\": \"김영록\",\n" +
                "      \"rprsvEmladr\": \"zerok92@naver.com\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        brno = "1568103314";

        ObjectMapper mapper = new ObjectMapper();
        object = mapper.readValue(json, Map.class);
    }

    @Test
    @DisplayName("API_통신_성공")
    void api_Ok() throws Exception {
        // Mock 설정
        when(publicDataService.getPublicCorporateData(brno))
                .thenReturn(ResponseEntity.ok(object));

        Map<String, Object> result = publicDataService.getPublicCorporateData(brno).getBody();

        assertFalse(result.isEmpty());
    }
}