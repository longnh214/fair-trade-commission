package com.example.trade.service;


import com.example.trade.feignClient.FtcClient;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FtcService {
    private final FtcClient ftcClient;
    @Value("${external.ftc.defaultArgs}")
    private String ftcArgs;

    @Async
    public CompletableFuture<Response> downloadCsvAsync(String fileName){
        Response response = ftcClient.downloadCsv(ftcArgs, fileName);
        return CompletableFuture.completedFuture(response);
    }

    public Response downloadCsvForRegion(String fileName) throws IOException, ExecutionException, InterruptedException {
        return downloadCsvAsync(fileName).get();
    }
}