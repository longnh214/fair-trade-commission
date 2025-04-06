package com.example.trade.service;

import com.example.trade.dto.FtcDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvFilterService {
    @Value("${external.corporate}")
    private String corporate;

    public List<FtcDataDto> filterIsCorporate(List<FtcDataDto> dtoList) {
        return dtoList.stream()
                .filter(dto -> dto.getIsCorporation().equals(corporate))
                .collect(Collectors.toList());
    }
}
