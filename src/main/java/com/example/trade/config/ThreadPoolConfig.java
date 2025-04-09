package com.example.trade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {
    @Bean(name = "customExecutor")
    public ExecutorService customExecutor() {
        int coreCount = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(
                coreCount * 2,
                coreCount * 4,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10000000), // 큐 크기: 넉넉하게
                new ThreadPoolExecutor.CallerRunsPolicy() // 거부 시 현재 스레드가 실행
        );
    }
}
