package com.example.trade.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "addressSearchClient", url = "${external.address.url}")
public interface AddressSearchClient {

    @GetMapping(value = "/addrLinkApiJsonp.do", produces = "application/json")
    ResponseEntity<Map<String, Object>> getAddressData(@RequestParam("confmKey") String confmKey,
                                                       @RequestParam("resultType") String resultType,
                                                       @RequestParam("keyword") String keyword);
}
