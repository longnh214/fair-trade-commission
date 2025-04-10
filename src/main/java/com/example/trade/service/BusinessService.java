package com.example.trade.service;

import com.example.trade.constant.City;
import com.example.trade.constant.DistrictMap;
import com.example.trade.dto.BusinessInputDto;
import com.example.trade.dto.FtcDataDto;
import com.example.trade.entity.CorporateInfo;
import com.example.trade.exception.InvalidBusinessNumberException;
import com.example.trade.exception.InvalidDistrictException;
import com.example.trade.repository.CorporateInfoRepository;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BusinessService {
    @Value("${external.ftc.filePrefix}")
    private String filePrefix;

    @Value("${external.ftc.fileAllPrefix}")
    private String fileAllPrefix;

    @Value("${external.ftc.foreignFileName}")
    private String foreignFileName;

    @Value("${external.batch-size}")
    private int batchSize;
    private final FtcService ftcService;
    private final FtcDataParsingService ftcDataParsingService;
    private final CsvParsingService csvParsingService;
    private final CsvFilterService csvFilterService;
    private final PublicDataService publicDataService;
    private final AddressSearchService addressSearchService;
    private final DistrictMap districtMap;
    private final CorporateInfoRepository corporateInfoRepository;
    private final ExecutorService customExecutor;

    public void process(BusinessInputDto inputDto) throws IOException {
        if(!cityAndDistinctValid(inputDto.getCity(), inputDto.getDistrict())){
            throw new InvalidDistrictException(inputDto.getCity(), inputDto.getDistrict());
        }
        String city = inputDto.getCity().getKorName();
        String district = inputDto.getDistrict();
        String fileName = makeFileName(city, district);

        CompletableFuture<Response> downloadFuture =
                inputDto.getCity() == City.FOREIGN
                        ? ftcService.downloadXlsAsync()
                        : ftcService.downloadCsvAsync(fileName);

        downloadFuture
                .thenApplyAsync(resource -> {
                    if(resource.status() != 200){
                        throw new RuntimeException("CSV 다운로드 실패");
                    }

                    try (InputStream inputStream = resource.body().asInputStream()) {
                        List<FtcDataDto> parsedList = ftcDataParsingService.parseToDto(inputDto.getCity(), inputStream);

                        log.info("통신판매사업자 파일 파싱 완료 - 총 행 수: {}", parsedList.size());
                        return parsedList;
                    } catch (Exception e) {
                        throw new RuntimeException("통신판매사업자 파일 파싱 실패", e);
                    }
                }, customExecutor)
                .thenApplyAsync(parsedList -> {
                    List<FtcDataDto> filteredList = csvFilterService.filterIsCorporate(parsedList);
                    log.info("법인 필터링 완료 - 남은 건수: {}", filteredList.size());
                    return filteredList;
                }, customExecutor)
                .thenComposeAsync(filteredList -> {
                    List<CompletableFuture<CorporateInfo>> futures = filteredList.stream()
                            .map(data -> processSingleItem(data)
                                    .exceptionally(ex -> {
                                        log.error("데이터 가공 중 오류 발생: {}", ex.getMessage(), ex);
                                        return null;
                                    }))
                            .collect(Collectors.toList());

                    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                            .thenApply(v -> futures.stream()
                                    .map(CompletableFuture::join)
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList()));
                }, customExecutor)
                .thenAcceptAsync(completedList -> {
                    log.info("가공 성공한 데이터 수: {}", completedList.size());
                    saveInBatches(completedList, batchSize, customExecutor);
                }, customExecutor)
                .exceptionally(ex -> {
                    log.error("CSV 처리 중 오류 발생: {}", ex.getMessage(), ex);
                    return null;
                });
    }

    public void saveInBatches(List<CorporateInfo> dataList, int batchSize, ExecutorService executor) {
        int totalSize = dataList.size();
        int batchCount = (int) Math.ceil((double) totalSize / batchSize);

        for(int i=0;i<batchCount;i++){
            int fromIndex = i * batchSize;
            int toIndex = Math.min(fromIndex + batchSize, totalSize);

            List<CorporateInfo> batch = dataList.subList(fromIndex, toIndex);

            CompletableFuture.runAsync(() -> {
                try {
                    saveBatch(batch);
                    log.info("배치 저장 완료: {} ~ {}", fromIndex, toIndex);
                } catch (Exception e) {
                    log.error("배치 저장 중 오류 발생 ({} ~ {}): {}", fromIndex, toIndex, e.getMessage(), e);
                }
            }, executor);
        }
    }

    private void saveBatch(List<CorporateInfo> batch) {
        corporateInfoRepository.saveAll(batch);
    }

    private CompletableFuture<CorporateInfo> processSingleItem(FtcDataDto data) {
        if (Objects.isNull(data.getBusinessNumber()) || data.getBusinessNumber().isBlank()) {
            log.warn("잘못된 사업자 번호: {}", data.getBusinessNumber());
            return CompletableFuture.failedFuture(new InvalidBusinessNumberException("사업자 번호가 존재하지 않습니다.(null or blank)"));
        }

        try {
            String businessNumber = data.getBusinessNumber().replace("-", "");

            CompletableFuture<String> publicDataFuture = publicDataService.getPublicCorporateDataAsync(businessNumber)
                    .thenApply(response -> {
                        try {
                            return extractCorporateNumber(response);
                        } catch (Exception e) {
                            log.error("공공데이터에서 법인등록번호 추출 실패 - 번호: {}, 에러: {}", businessNumber, e.getMessage(), e);
                            throw new RuntimeException("법인등록번호 추출 실패", e);
                        }
                    });

            CompletableFuture<String> districtCodeFuture = addressSearchService.getAddressDataAsync(data.getAddress())
                    .thenApply(response -> {
                        try {
                            return extractDistrictCode(response);
                        } catch (Exception e) {
                            log.error("주소에서 행정동코드 추출 실패 - 주소: {}, 에러: {}", data.getAddress(), e.getMessage(), e);
                            throw new RuntimeException("행정동코드 추출 실패", e);
                        }
                    });

            return publicDataFuture.thenCombine(districtCodeFuture, (corporateNumber, districtCode) ->
                    CorporateInfo.builder()
                            .businessName(data.getBusinessName())
                            .businessNumber(data.getBusinessNumber())
                            .commSaleNumber(data.getCommSaleNumber())
                            .districtCode(districtCode)
                            .corporateNumber(corporateNumber)
                            .build()
            );

        } catch (Exception e) {
            log.error("processSingleItem 처리 중 예외 발생: {}", e.getMessage(), e);
            return CompletableFuture.failedFuture(e);
        }
    }

    public String extractDistrictCode(Map<String, Object> response) {
        try {
            Object resultsObj = response.get("results");
            if (!(resultsObj instanceof Map)) return null;
            Map<String, Object> resultsMap = (Map<String, Object>) resultsObj;

            Object jusoObj = resultsMap.get("juso");
            if (!(jusoObj instanceof List)) return null;
            List<?> jusoList = (List<?>) jusoObj;

            if (jusoList.isEmpty()) return null;
            Object firstJusoObj = jusoList.get(0);
            if (!(firstJusoObj instanceof Map)) return null;
            Map<String, Object> firstJusoMap = (Map<String, Object>) firstJusoObj;

            Object admCd = firstJusoMap.get("admCd");

            return admCd != null ? admCd.toString() : null;
        } catch (Exception e) {
            log.error("주소 API 포맷 에러");
            return null;
        }
    }

    public String extractCorporateNumber(Map<String, Object> response) {
        try {
            Object itemsObj = response.get("items");

            if (!(itemsObj instanceof List)) {
                log.warn("items가 List가 아님: {}", itemsObj);
                return null;
            }

            List<?> itemsList = (List<?>) itemsObj;
            if (itemsList.isEmpty()) return null;

            Object firstItem = itemsList.get(0);
            if (!(firstItem instanceof Map)) return null;

            Map<String, Object> itemMap = (Map<String, Object>) firstItem;
            Object crno = itemMap.get("crno");

            return crno != null ? crno.toString() : null;

        } catch (Exception e) {
            log.error("법인 상세조회 API 포맷 에러: {}", e.getMessage(), e);
            return null;
        }
    }

    public String makeFileName(String city, String district){
        String fileName;
        if(city.equals(City.FOREIGN)){
            fileName = foreignFileName;
        }else if(district.equals("전체")){
            fileName = fileAllPrefix + city + " " + district + ".csv";
        }else{
            fileName = filePrefix + city + "_" + district + ".csv";
        }
        
        return URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");
    }

    public boolean cityAndDistinctValid(City city, String district){
        return districtMap.isValid(city, district);
    }
}