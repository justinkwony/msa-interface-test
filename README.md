# msa-interface-test
Micro Service Architecture에서 App간 인터페이스(Rest vs gRPC) 간 속도 비교 테스트

## Build
```
$ ./gradlew bootJar
```
## Run
두개의 Node에서 각각 실행 또는 하나의 Node에서 다른 Port로 실행
```
$ java -Dserver.port=8081 -Dgrpc.server.port=9081 \
    -Dlogging.level.com.nonghyupit=info \
    -Drest.client.converterRest.address=http://192.168.100.25:8082 \
    -Dgrpc.client.converterGrpcImpl.address=static://192.168.100.25:9082 \
    -jar ./msa-interface-test-1.0.jar
```
```
$ java -Dserver.port=8082 -Dgrpc.server.port=9082 \
    -Dlogging.level.com.nonghyupit=info \
    -Drest.client.converterRest.address=http://192.168.100.24:8081 \
    -Dgrpc.client.converterGrpcImpl.address=static://192.168.100.24:9081 \
    -jar ./msa-interface-test-1.0.jar
```
```
$ curl http://192.168.100.24:8081/{type}/{count}
$ curl http://192.168.100.24:8081/rest/1000
```
지정한 방식(type : rest | grpc)으로 1000(count)회 호출 후 경과시간 표시

## 테스트 앱 구성
앱간 내부 통신 속도를 부각시키기 위해 단순 로직(대문자로 바꾸기)을 사용\
1회 호출시 내부호출을 {count} 반복 호출 후 리턴\
유저 호출 -> [(앱1 --> 앱2) x {count}]

## 테스트 환경
```
+------------------------+    +------------------------+
|         Node 1         |    |         Node 2         |
| +------+      +------+ |    | +------|      +------+ |      
| | 8082 | <==> | 8081 | <====> | 8081 | <==> | 8082 | |
| +------+      +------+ |    | +------|      +------+ |
+------------------------+    +------------------------+
```
2-node 테스트시 http://node1:8081 \
1-node 테스트시 http://node1:8082 \

Kubernetes\
테스트 앱 복제수: 4 Pods

## 테스트 시나리오
부하발생기: Apache JMeter 5.5\
가상사용자(Thread)를 10명씩 늘려가며 2분간 지속
> Number of Threads: 10 ~ 서버에러 발생시 까지\
> Ramp-up period: 120 sec\
> Loop Count: Infinite\
> Duration: 130 sec

## 테스트 결과
| Users | Executions | | 90% 평균 ms | |
|-------|-------:|--------:|----:|----:|
|Threads| Rest   | gRPC    |Rest |gRPC |
|    10 | 36,385 |  29,584 |  32 |  31 |
|    20 | 61,445 |  52,873 |  61 |  24 |
|    30 | 39,108 |  97,149 |  80 |  33 |
|    40 | 35,343 | 100,707 | 162 |  38 |
|    50 | 79,400 | 119,916 | 116 |  50 |
|    60 | 79,619 | 126,343 | 122 |  52 |
|    70 | 75,226 | 142,423 | 173 |  53 |
|    80 | 85,080 | 122,011 | 208 |  84 |
|    90 | 88,214 | 151,150 | 207 |  64 |
|   100 | 91,416 | 148,903 | 244 |  72 |
|   110 | 98,680 | 178,640 | 228 |  78 |
|   120 | Error  | 178,502 |     |  82 |
|   130 |        | 168,636 |     | 100 |
|   140 |        | 186,747 |     |  95 |
|   150 |        | 172,288 |     | 106 |
|   160 |        | 176,109 |     | 105 |
|   170 |        | 244,077 |     |  88 |
|   180 |        | 197,923 |     | 117 |
|   190 |        | 257,438 |     |  94 |
|   200 |        | 250,842 |     | 105 |
|   210 |        | 258,934 |     | 109 |
|   220 |        | 263,203 |     | 109 |
|   230 |        | 267,270 |     | 120 |
|   240 |        | 265,969 |     | 112 |
|   250 |        | 259,201 |     | 119 |
|   260 |        | 249,763 |     | 140 |
|   270 |        | Error   |     |     |
