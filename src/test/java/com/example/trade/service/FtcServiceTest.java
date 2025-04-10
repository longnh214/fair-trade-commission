package com.example.trade.service;

import com.example.trade.feignClient.FtcClient;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FtcServiceTest {

    @Mock
    private FtcClient ftcClient;
    @InjectMocks
    private FtcService ftcService;
    private String csvData;
    private ByteArrayInputStream inputStream;
    private String fileName;
    @Mock
    private Response response;

    @BeforeEach
    void setUp() {
        // given
        csvData = "통신판매번호,신고기관명,상호,사업자등록번호,법인여부,대표자명,전화번호,전자우편,신고일자,사업장소재지,사업장소재지(도로명),업소상태,신고기관 대표연락처,판매방식,취급품목,인터넷도메인,호스트서버소재지\n" +
                "2025-전북장수-0013,전북특별자치도 장수군,엠티엠,487-23-02072,개인,길승재,010-6222-****,kil****@naver.com,20250404,전북특별자치도 장수군 천천면 월곡리 ***-*  ,전북특별자치도 장수군 천천면 박실길 **-*,정상영업,null,인터넷,종합몰,null,null\n" +
                "2025-전북장수-0012,전북특별자치도 장수군,장수향,475-41-00022,개인,서미나,010-5135-****,bin*****@naver.com,20250403,전북특별자치도 장수군 장수읍 장수리 ***-** 장수시장  ,전북특별자치도 장수군 장수읍 시장로 ** 장수시장,정상영업,null,인터넷,건강/식품,null,null";

        fileName = "some_file.csv";

        inputStream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));

        response = Response.builder()
                .status(200)
                .reason("OK")
                .headers(Collections.emptyMap())
                .body(inputStream, csvData.length())
                .request(Request.create(Request.HttpMethod.GET, "http://example.com", Collections.emptyMap(), null, Charset.defaultCharset(), null))
                .build();
    }

    @Test
    @DisplayName("CSV_다운로드_성공")
    void download_success() throws Exception {
        // when
        doReturn(response).when(ftcService).downloadCsv(any());

        InputStream csvInputStream = ftcService.downloadCsv(fileName).body().asInputStream();

        // then
        assertTrue(csvInputStream.available() > 0);
    }
}