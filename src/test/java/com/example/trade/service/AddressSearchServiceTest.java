package com.example.trade.service;

import com.example.trade.feignClient.AddressSearchClient;
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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AddressSearchServiceTest {
    @Mock
    AddressSearchClient addressSearchClient;

    @InjectMocks
    AddressSearchService addressSearchService;

    String json;
    Map object;
    String keyword;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        keyword = "용구대로2787번길 21-17";

        json = "{\n" +
                "    \"results\": {\n" +
                "        \"common\": {\n" +
                "            \"errorMessage\": \"정상\",\n" +
                "            \"countPerPage\": \"10\",\n" +
                "            \"totalCount\": \"1\",\n" +
                "            \"errorCode\": \"0\",\n" +
                "            \"currentPage\": \"1\"\n" +
                "        },\n" +
                "        \"juso\": [\n" +
                "            {\n" +
                "                \"detBdNmList\": \"\",\n" +
                "                \"engAddr\": \"21-17 Yonggu-daero 2787beon-gil, Suji-gu, Yongin-si, Gyeonggi-do\",\n" +
                "                \"rn\": \"용구대로2787번길\",\n" +
                "                \"emdNm\": \"죽전동\",\n" +
                "                \"zipNo\": \"16865\",\n" +
                "                \"roadAddrPart2\": \" (죽전동)\",\n" +
                "                \"emdNo\": \"01\",\n" +
                "                \"sggNm\": \"용인시 수지구\",\n" +
                "                \"jibunAddr\": \"경기도 용인시 수지구 죽전동 1003-619\",\n" +
                "                \"siNm\": \"경기도\",\n" +
                "                \"roadAddrPart1\": \"경기도 용인시 수지구 용구대로2787번길 21-17\",\n" +
                "                \"bdNm\": \"\",\n" +
                "                \"admCd\": \"4146510200\",\n" +
                "                \"udrtYn\": \"0\",\n" +
                "                \"lnbrMnnm\": \"1003\",\n" +
                "                \"roadAddr\": \"경기도 용인시 수지구 용구대로2787번길 21-17 (죽전동)\",\n" +
                "                \"lnbrSlno\": \"619\",\n" +
                "                \"buldMnnm\": \"21\",\n" +
                "                \"bdKdcd\": \"0\",\n" +
                "                \"liNm\": \"\",\n" +
                "                \"rnMgtSn\": \"414654415120\",\n" +
                "                \"mtYn\": \"0\",\n" +
                "                \"bdMgtSn\": \"4146510200110030619028016\",\n" +
                "                \"buldSlno\": \"17\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        object = mapper.readValue(json, Map.class);
    }

    @Test
    @DisplayName("주소_검색_API_동작")
    void addressSearch() throws Exception{
        when(addressSearchService.getAddressData(keyword))
                .thenReturn(object);

        Map<String, Object> result = addressSearchService.getAddressData(keyword);

        assertNotNull(result);
    }
}