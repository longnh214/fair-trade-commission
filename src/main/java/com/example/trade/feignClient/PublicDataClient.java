package com.example.trade.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "publicDataClient", url = "${external.public-data.url}")
public interface PublicDataClient {
    @GetMapping(value = "/getMllBsInfoDetail_2", produces = "application/json")
    Map<String, Object> getPublicCorporateData(@RequestParam("serviceKey") String serviceKey,
                                               @RequestParam("pageNo") String pageNo,
                                               @RequestParam("numOfRows") String numOfRows,
                                               @RequestParam("resultType") String resultType,
                                               @RequestParam("brno") String brno);
}
