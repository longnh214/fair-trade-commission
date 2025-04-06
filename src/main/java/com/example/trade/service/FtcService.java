package com.example.trade.service;


import com.example.trade.feignClient.FtcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FtcService {
    private final FtcClient ftcClient;
    @Value("${external.ftc.defaultArgs}")
    private String ftcArgs;

    public ResponseEntity<Resource> downloadCsvForRegion(String fileName) {
        return ftcClient.downloadCsv(ftcArgs, fileName);
    }
}