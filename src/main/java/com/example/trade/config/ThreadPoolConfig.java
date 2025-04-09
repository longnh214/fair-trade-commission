package com.example.trade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {
    @Bean(name = "customExecutor")
    public ExecutorService customExecutor() {
        int coreCount = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(
                coreCount * 2, // corePoolSize
                coreCount * 4, // maximumPoolSize
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10000000), // 큐 크기: 넉넉하게
                new ThreadPoolExecutor.CallerRunsPolicy() // 거부 시 현재 스레드가 실행
        );
    }
}
