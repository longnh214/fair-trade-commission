package com.example.trade.service;

import com.example.trade.dto.FtcDataDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Log4j2
public class CsvParsingService {

    public List<FtcDataDto> parse(InputStream csvInputStream) {
        List<FtcDataDto> resultList = new ArrayList<>();

        try {
            CsvToBean<FtcDataDto> csvToBean = new CsvToBeanBuilder<FtcDataDto>(
                    new InputStreamReader(csvInputStream, StandardCharsets.UTF_8))
                    .withType(FtcDataDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withSeparator(',')
                    .build();

            Iterator<FtcDataDto> iterator = csvToBean.iterator();

            int rowNum = 1;
            while (iterator.hasNext()) {
                try {
                    FtcDataDto dto = iterator.next();
                    resultList.add(dto);
                } catch (Exception e) {
                    log.warn("{} 번째 행 건너뜀 (파싱 오류): {}", rowNum, e.getMessage());
                }
                rowNum++;
            }

            log.info("최종 파싱된 데이터 건수: {}", resultList.size());
            return resultList;

        } catch (Exception e) {
            log.error("CSV 파싱 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("CSV 파싱 실패", e);
        }
    }
}