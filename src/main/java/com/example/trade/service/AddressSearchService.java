package com.example.trade.service;

import com.example.trade.feignClient.AddressSearchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AddressSearchService {
    private final AddressSearchClient addressSearchClient;

    private static final String JSON = "json";
    @Value("${external.address.service-key}")
    private String confmKey;

    @Async
    public CompletableFuture<Map<String, Object>> getAddressDataAsync(String keyword) {
        Map<String, Object> response = addressSearchClient.getAddressData(confmKey, JSON, keyword);
        return CompletableFuture.completedFuture(response);
    }

    public Map<String, Object> getAddressData(String keyword) throws ExecutionException, InterruptedException {
        return getAddressDataAsync(keyword).get();
    }
}
