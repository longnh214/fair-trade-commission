package com.example.trade.service;


import com.example.trade.feignClient.FtcClient;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${external.ftc.key}")
    private String key;

    @Async("customExecutor")
    public CompletableFuture<Response> downloadCsvAsync(String fileName){
        Response response = ftcClient.downloadCsv(ftcArgs, fileName);
        return CompletableFuture.completedFuture(response);
    }

    @Async("customExecutor")
    public CompletableFuture<Response> downloadXlsAsync(){
        Response response = ftcClient.downloadXls(key);
        return CompletableFuture.completedFuture(response);
    }

    public Response downloadCsv(String fileName) throws ExecutionException, InterruptedException {
        return downloadCsvAsync(fileName).get();
    }
}