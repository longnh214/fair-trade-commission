package com.example.trade.service;

import com.example.trade.dto.FtcDataDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CsvFilterServiceTest {
    @Autowired
    private CsvParsingService csvParsingService;
    @Autowired
    private CsvFilterService csvFilterService;
    private String csvData;
    private InputStream inputStream;
    private List<FtcDataDto> dtoList;

    @BeforeEach
    void setUp() throws IOException {
        csvData = "통신판매번호,신고기관명,상호,사업자등록번호,법인여부,대표자명,전화번호,전자우편,신고일자,사업장소재지,사업장소재지(도로명),업소상태,신고기관 대표연락처,판매방식,취급품목,인터넷도메인,호스트서버소재지\n" +
                "2025-전북장수-0013,전북특별자치도 장수군,엠티엠,487-23-02072,개인,길승재,010-6222-****,kil****@naver.com,20250404,전북특별자치도 장수군 천천면 월곡리 ***-*  ,전북특별자치도 장수군 천천면 박실길 **-*,정상영업,null,인터넷,종합몰,null,null\n" +
                "2025-전북장수-0012,전북특별자치도 장수군,장수향,475-41-00022,법인,서미나,010-5135-****,bin*****@naver.com,20250403,전북특별자치도 장수군 장수읍 장수리 ***-** 장수시장  ,전북특별자치도 장수군 장수읍 시장로 ** 장수시장,정상영업,null,인터넷,건강/식품,null,null";

        inputStream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));

        dtoList = csvParsingService.parseToDto(inputStream);
    }

    @Test
    @DisplayName("filter_테스트")
    void filter(){
        List<FtcDataDto> filterFtcDataDtoList = csvFilterService.filterIsCorporate(dtoList);

        assertFalse(filterFtcDataDtoList.isEmpty());
    }
}