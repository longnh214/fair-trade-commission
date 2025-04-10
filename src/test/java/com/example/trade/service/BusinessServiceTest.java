package com.example.trade.service;

import com.example.trade.constant.City;
import com.example.trade.dto.BusinessInputDto;
import com.example.trade.dto.FtcDataDto;
import com.example.trade.repository.CorporateInfoRepository;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BusinessServiceTest {

    @Autowired
    private BusinessService businessService;

    @MockitoBean
    private PublicDataService publicDataService;

    @MockitoBean
    private AddressSearchService addressSearchService;

    @MockitoBean
    private FtcService ftcService;

    @MockitoBean
    private FtcDataParsingService ftcDataParsingService;

    @MockitoBean
    private XlsParsingService xlsParsingService;

    @MockitoBean
    private CsvParsingService csvParsingService;

    @MockitoBean
    private CsvFilterService csvFilterService;

    @MockitoBean
    private CorporateInfoRepository corporateInfoRepository;

    private BusinessInputDto input;

    private String csvData;

    private ByteArrayInputStream inputStream;

    private List<FtcDataDto> mockDtos;

    private Response mockResponse;

    @BeforeEach
    void setUp(){
        // given
        input = BusinessInputDto.builder()
                .city(City.SEOUL)
                .district("강남구")
                .build();

        csvData = "2025-서울강남-01968,서울특별시 강남구,구이공,149-85-02810,법인,LI HUA(이화),'010 - 4086 - 9940,u920@ttservice.co.kr,20250407,서울특별시 강남구 역삼동 736-38  ,서울특별시 강남구 논현로87길 19^ 7층 (역삼동),정상영업,02-3423-5382,인터넷,종합몰,쿠팡  스마트스토어,null";

        inputStream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));

        mockDtos = List.of(
                new FtcDataDto("2025-서울강남-01968", "서울특별시 강남구", "구이공", "149-85-02810", "법인",
                        "LI HUA(이화)", "'010 - 4086 - 9940", "u920@ttservice.co.kr", "20250407",
                        "서울특별시 강남구 역삼동 736-38", "서울특별시 강남구 논현로87길 19^ 7층 (역삼동)",
                        "정상영업", "02-3423-5382", "인터넷", "종합몰", "쿠팡 스마트스토어", "null")
        );

        mockResponse = Response.builder()
                .status(200)
                .reason("OK")
                .headers(Collections.emptyMap())
                .body(inputStream, csvData.length())
                .request(Request.create(Request.HttpMethod.GET, "http://example.com", Collections.emptyMap(), null, Charset.defaultCharset(), null))
                .build();
    }

    @Test
    @DisplayName("비즈니스 로직 통합 테스트 - 비동기 저장 검증")
    void testBusinessServiceProcess() throws Exception {// CountDownLatch 선언
        CountDownLatch latch = new CountDownLatch(1);

        // mock 설정
        doReturn(CompletableFuture.completedFuture(mockResponse)).when(ftcService).downloadCsvAsync(eq("%ED%86%B5%EC%8B%A0%ED%8C%90%EB%A7%A4%EC%82%AC%EC%97%85%EC%9E%90_%EC%84%9C%EC%9A%B8%ED%8A%B9%EB%B3%84%EC%8B%9C_%EA%B0%95%EB%82%A8%EA%B5%AC.csv"));
        doReturn(mockDtos).when(ftcDataParsingService).parseToDto(any(), eq(inputStream));
        doReturn(mockDtos).when(csvFilterService).filterIsCorporate(any());
        doReturn(CompletableFuture.completedFuture(Map.of("items", List.of(Map.of("crno", "1101111111111")))))
                .when(publicDataService).getPublicCorporateDataAsync(any());
        doReturn(CompletableFuture.completedFuture(Map.of("results", Map.of("juso", List.of(Map.of("admCd", "11110101"))))))
                .when(addressSearchService).getAddressDataAsync(any());

        // when
        businessService.process(input);

        // then
        latch.await(5, TimeUnit.SECONDS);

        verify(corporateInfoRepository, atLeastOnce()).saveAll(any());
    }
}