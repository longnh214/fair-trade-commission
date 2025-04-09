package com.example.trade.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "addressSearchClient", url = "${external.address.url}")
public interface AddressSearchClient {

    @GetMapping(value = "/addrLinkApi.do", produces = "application/json")
    Map<String, Object> getAddressData(@RequestParam("confmKey") String confmKey,
                                       @RequestParam("resultType") String resultType,
                                       @RequestParam("keyword") String keyword);
}
