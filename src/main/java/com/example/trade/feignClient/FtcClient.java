package com.example.trade.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ftcClient", url = "${external.ftc.url}")
public interface FtcClient {
    @GetMapping(value = "/www/downloadBizComm.do", produces = "text/csv")
    ResponseEntity<Resource> downloadCsv(@RequestParam("atchFileUrl") String fileUrl,
                                         @RequestParam("atchFileNm") String fileName);
}