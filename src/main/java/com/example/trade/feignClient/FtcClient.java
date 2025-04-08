package com.example.trade.feignClient;

import feign.Headers;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ftcClient", url = "${external.ftc.url}")
public interface FtcClient {
    @GetMapping(value = "/downloadBizComm.do", produces = "text/csv")
    @Headers({
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Whale/4.30.291.11 Safari/537.36",
            "Accept: */*",
            "Referer: https://www.ftc.go.kr/www/selectBizCommOpenList.do?key=255",
            "Host: www.ftc.go.kr",
            "Connection: keep-alive",
            "Accept-Encoding: gzip, deflate, br"
    })
    Response downloadCsv(@RequestParam("atchFileUrl") String fileUrl,
                         @RequestParam("atchFileNm") String fileName);
}