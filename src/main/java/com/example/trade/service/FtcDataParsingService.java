package com.example.trade.service;

import com.example.trade.constant.City;
import com.example.trade.dto.FtcDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FtcDataParsingService {

    private final CsvParsingService csvParsingService;
    private final XlsParsingService xlsParsingService;

    public List<FtcDataDto> parseToDto(City city, InputStream inputStream) throws Exception {
        if (city == City.FOREIGN) {
            return xlsParsingService.parseToDto(inputStream);
        } else {
            return csvParsingService.parseToDto(inputStream);
        }
    }
}