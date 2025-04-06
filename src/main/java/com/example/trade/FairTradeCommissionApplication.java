package com.example.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FairTradeCommissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FairTradeCommissionApplication.class, args);
    }

}
