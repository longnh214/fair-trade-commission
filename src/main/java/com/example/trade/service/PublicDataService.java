package com.example.trade.service;


import com.example.trade.feignClient.PublicDataClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PublicDataService {
    private final PublicDataClient publicDataClient;

    @Value("${external.public-data.service-key}")
    private String serviceKey;

    private static final String ONE = "1";

    @Async
    public CompletableFuture<ResponseEntity<Map<String, Object>>> getPublicCorporateDataAsync(String brno) {
        ResponseEntity<Map<String, Object>> response = publicDataClient.getPublicCorporateData(serviceKey, ONE, ONE, brno);
        return CompletableFuture.completedFuture(response);
    }

    public ResponseEntity<Map<String, Object>> getPublicCorporateData(String brno) throws ExecutionException, InterruptedException {
        return getPublicCorporateDataAsync(brno).get();
    }
}
