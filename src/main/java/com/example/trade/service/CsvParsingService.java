package com.example.trade.service;

import com.example.trade.dto.FtcDataDto;
import com.example.trade.util.CharsetDetectorUtil;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.UnescapedQuoteHandling;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CsvParsingService {
    private final CharsetDetectorUtil charsetDetectorUtil;
    
    public List<FtcDataDto> parseToDto(InputStream csvInputStream) throws IOException {
        // Bean Processor 준비
        BeanListProcessor<FtcDataDto> rowProcessor = new BeanListProcessor<>(FtcDataDto.class);

        byte [] bytes = csvInputStream.readAllBytes();
        Charset charset = charsetDetectorUtil.detectCharset(new ByteArrayInputStream(bytes), Charset.forName("EUC-KR"));

        // CSV 파서 설정
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true); // 헤더를 DTO 필드에 매핑
        settings.setProcessor(rowProcessor);
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setDelimiterDetectionEnabled(true, ',');
        settings.setIgnoreLeadingWhitespaces(true);
        settings.setIgnoreTrailingWhitespaces(true);
        settings.setMaxCharsPerColumn(10000000);
        settings.setUnescapedQuoteHandling(UnescapedQuoteHandling.STOP_AT_CLOSING_QUOTE);
        settings.setSkipEmptyLines(true);

        // 파서 생성
        CsvParser parser = new CsvParser(settings);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes), charset))) {
            parser.parse(reader);
        }

        return rowProcessor.getBeans();
    }
}