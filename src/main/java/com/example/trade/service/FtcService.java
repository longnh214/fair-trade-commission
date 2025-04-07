package com.example.trade.service;


import com.example.trade.feignClient.FtcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FtcService {
    private final FtcClient ftcClient;
    @Value("${external.ftc.defaultArgs}")
    private String ftcArgs;

    @Async
    public CompletableFuture<ResponseEntity<Resource>> downloadCsvAsync(String fileName) {
        ResponseEntity<Resource> response = ftcClient.downloadCsv(ftcArgs, fileName);
        return CompletableFuture.completedFuture(response);
    }

    public ResponseEntity<Resource> downloadCsvForRegion(String fileName) throws ExecutionException, InterruptedException {
        return downloadCsvAsync(fileName).get();
    }
}