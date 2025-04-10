# fair-trade-commission

### application.yml

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