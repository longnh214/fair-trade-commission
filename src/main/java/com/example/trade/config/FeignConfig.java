package com.example.trade.config;

import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate
                .header("Accept", "application/json, text/csv")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Whale/4.30.291.11 Safari/537.36")
                .header("Accept-Encoding", "gzip, deflate, br");
    }

    @Bean
    public Retryer.Default retryer() {
        return new Retryer.Default(1000L, TimeUnit.SECONDS.toMillis(3L), 5);
    }
}
