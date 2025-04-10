package com.example.trade.service;


import com.example.trade.exception.APIDailyRequestLimitExceededException;
import com.example.trade.feignClient.PublicDataClient;
import feign.codec.DecodeException;
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

    @Async("customExecutor")
    public CompletableFuture<Map<String, Object>> getPublicCorporateDataAsync(String brno) {
        Map<String, Object> response;
        try {
            response = publicDataClient.getPublicCorporateData(serviceKey, ONE, TEN, JSON, brno);
        }catch(DecodeException e){
            throw new APIDailyRequestLimitExceededException("법인 상세 등록조회 API 일일 요청 한도를 초과했습니다.");
        }
        return CompletableFuture.completedFuture(response);
    }

    public Map<String, Object> getPublicCorporateData(String brno) throws ExecutionException, InterruptedException {
        return getPublicCorporateDataAsync(brno).get();
    }
}