package com.example.trade.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.example.trade.feignClient.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FtcServiceTest {

    @Mock
    private FtcClient ftcClient;
    @InjectMocks
    private FtcService ftcService;
    private String csvData;
    private Resource mockResource;

    @BeforeEach
    void setUp() {
        csvData = "통신판매번호,신고기관명,상호,사업자등록번호,법인여부,대표자명,전화번호,전자우편,신고일자,사업장소재지,사업장소재지(도로명),업소상태,신고기관 대표연락처,판매방식,취급품목,인터넷도메인,호스트서버소재지\n" +
                "2025-전북장수-0013,전북특별자치도 장수군,엠티엠,487-23-02072,개인,길승재,010-6222-****,kil****@naver.com,20250404,전북특별자치도 장수군 천천면 월곡리 ***-*  ,전북특별자치도 장수군 천천면 박실길 **-*,정상영업,null,인터넷,종합몰,null,null\n" +
                "2025-전북장수-0012,전북특별자치도 장수군,장수향,475-41-00022,개인,서미나,010-5135-****,bin*****@naver.com,20250403,전북특별자치도 장수군 장수읍 장수리 ***-** 장수시장  ,전북특별자치도 장수군 장수읍 시장로 ** 장수시장,정상영업,null,인터넷,건강/식품,null,null";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));
        mockResource = new InputStreamResource(inputStream);
    }

    @Test
    @DisplayName("CSV_다운로드_성공")
    void download_success() throws Exception {
        // Mock 설정
        when(ftcService.downloadCsvForRegion("some_file.csv"))
                .thenReturn(ResponseEntity.ok(mockResource));

        InputStream csvInputStream = ftcService.downloadCsvForRegion("some_file.csv").getBody().getInputStream();

        assertTrue(csvInputStream.available() > 0);
    }



        // 서비스 로직 실행
//        InputStream csvInputStream = ftcService.downloadCsvForRegion("some_file.csv");
//        List<Map<String, String>> result = ftcService.parseCsv(csvInputStream);

        // 검증
//        assertEquals(2, result.size());
//        assertEquals("가게A", result.get(0).get("사업자명"));
//        assertEquals("010-2345-6789", result.get(1).get("전화번호"));
//    }
}