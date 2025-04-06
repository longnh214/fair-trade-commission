package com.example.trade.service;

import com.example.trade.dto.FtcDataDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CsvParsingService {
    public List<FtcDataDto> parse(InputStream csvInputStream) throws IOException {
        try (Reader reader = new InputStreamReader(csvInputStream, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            return StreamSupport.stream(csvParser.spliterator(), false)
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }
    }

    private FtcDataDto mapToDto(CSVRecord record) {
        return new FtcDataDto(
                record.get("통신판매번호"),
                record.get("신고기관명"),
                record.get("상호"),
                record.get("사업자등록번호"),
                record.get("법인여부"),
                record.get("대표자명"),
                record.get("전화번호"),
                record.get("전자우편"),
                record.get("신고일자"),
                record.get("사업장소재지"),
                record.get("사업장소재지(도로명)"),
                record.get("업소상태"),
                record.get("신고기관 대표연락처"),
                record.get("판매방식"),
                record.get("취급품목"),
                record.get("인터넷도메인"),
                record.get("호스트서버소재지")
        );
    }
}
