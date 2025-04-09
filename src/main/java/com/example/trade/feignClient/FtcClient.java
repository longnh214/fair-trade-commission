package com.example.trade.feignClient;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ftcClient", url = "${external.ftc.url}")
public interface FtcClient {
    @GetMapping(value = "/downloadBizComm.do", produces = "text/csv")
    Response downloadCsv(@RequestParam("atchFileUrl") String fileUrl,
                         @RequestParam("atchFileNm") String fileName);
}