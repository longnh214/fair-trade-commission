package com.example.trade.service;

import com.example.trade.dto.FtcDataDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class XlsParsingServiceTest {

    @Autowired
    private XlsParsingService xlsParsingService;
    private InputStream inputStream;

    @BeforeEach
    void setUp(){
        // given
        inputStream = getClass().getClassLoader().getResourceAsStream("test-data.xls");

        ReflectionTestUtils.setField(xlsParsingService, "corporate", "법인");
    }

    @Test
    @DisplayName("XLS_파싱_테스트")
    void parseToDto() throws Exception {
        // when
        List<FtcDataDto> result = xlsParsingService.parseToDto(inputStream);

        // then
        assertThat(result).isNotEmpty();
        FtcDataDto first = result.get(0);
        assertThat(first.getCommSaleNumber()).startsWith("2025");
        assertThat(first.getIsCorporate()).isEqualTo("법인");
        assertThat(first.getBusinessName()).startsWith("Giraffe");
        assertThat(first.getBusinessNumber()).startsWith("1234");
        assertThat(first.getAddress()).startsWith("45");
    }
}