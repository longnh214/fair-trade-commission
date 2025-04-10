# fair-trade-commission

## Dependency

```groovy
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.cloud:spring-cloud-starter-openfeign'
implementation 'com.googlecode.juniversalchardet:juniversalchardet:1.0.3'
implementation 'com.fasterxml.jackson.core:jackson-databind:2.17.0'
implementation 'com.univocity:univocity-parsers:2.9.1'
implementation 'org.apache.poi:poi:5.2.3'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation group: 'org.springdoc', name: 'springdoc-openapi-starter-webmvc-ui', version: '2.1.0'
implementation 'org.springframework.boot:spring-boot-starter-log4j2'
compileOnly 'org.projectlombok:lombok'
developmentOnly 'org.springframework.boot:spring-boot-devtools'
runtimeOnly 'com.h2database:h2'
annotationProcessor 'org.projectlombok:lombok'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'com.fasterxml.jackson.core:jackson-databind:2.17.0'
testImplementation 'org.mockito:mockito-core:5.11.0'
testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
```

## Project Structure

```
├── FairTradeCommissionApplication.java
├── config
│   ├── FeignConfig.java
│   ├── SwaggerConfig.java
│   └── ThreadPoolConfig.java
├── constant
│   ├── City.java
│   └── DistrictMap.java
├── controller
│   └── CorporateInfoController.java
├── dto
│   ├── BusinessInputDto.java
│   └── FtcDataDto.java
├── entity
│   └── CorporateInfo.java
├── exception
│   ├── APIDailyRequestLimitExceededException.java
│   ├── APIResponseFormatNotMatchException.java
│   ├── ExtractInfoFailException.java
│   ├── FileDownloadFailException.java
│   ├── FileParsingFailException.java
│   ├── InvalidBusinessNumberException.java
│   ├── InvalidCityNameException.java
│   └── InvalidDistrictException.java
├── feignClient
│   ├── AddressSearchClient.java
│   ├── FtcClient.java
│   └── PublicDataClient.java
├── repository
│   └── CorporateInfoRepository.java
├── service
│   ├── AddressSearchService.java
│   ├── BusinessService.java
│   ├── CsvFilterService.java
│   ├── CsvParsingService.java
│   ├── FtcDataParsingService.java
│   ├── FtcService.java
│   ├── PublicDataService.java
│   └── XlsParsingService.java
└── util
    └── CharsetDetectorUtil.java

```

## application.yml

### example
```yaml
spring:
  main:
    allow-bean-definition-overriding: true
  datasource:
    url: #h2 host url
    username: sa
    password:
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: #update or create
    properties:
      hibernate:
        jdbc:
          batch_size: 100  # 원하는 batch 크기 설정
        order_inserts: true
        order_updates: true
        generate_statistics: true
        show_sql: true
        format_sql: true

logging.level:
  org.hibernate.SQL: debug

feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 10000

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html

external:
  batch-size: 100 # bulk insert를 위한 원하는 batch 크기 설정 
  ftc:
    url: https://www.ftc.go.kr/www
    filePrefix: 통신판매사업자_
    fileAllPrefix: 통신판매사업자_ALL_
    foreignFileName: 국외통신판매사업자.xls
    defaultArgs: dataopen
    key: 255
  corporate: 법인
  public-data:
    url: http://apis.data.go.kr/1130000/MllBsDtl_2Service
    service-key: # 법인 등록 상세 API 서비스 키 (encoding)
  address:
    url: https://business.juso.go.kr/addrlink
    service-key: # 주소 API 호출 서비스 키
```