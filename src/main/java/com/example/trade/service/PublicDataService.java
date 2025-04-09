package com.example.trade.service;


import com.example.trade.feignClient.PublicDataClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private static final String TEN = "10";
    private static final String JSON = "json";

    @Async
    public CompletableFuture<Map<String, Object>> getPublicCorporateDataAsync(String brno) {
        Map<String, Object> response = publicDataClient.getPublicCorporateData(serviceKey, ONE, TEN, JSON, brno);
        return CompletableFuture.completedFuture(response);
    }

    public Map<String, Object> getPublicCorporateData(String brno) throws ExecutionException, InterruptedException {
        return getPublicCorporateDataAsync(brno).get();
    }
}
