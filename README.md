## Schema 생성
### CREATE DATABASE stock_example;
### USE stock_example; stock_example; 스키마 사용

### Pessimistic Lock
#### 실제 데이터에 Lock 을 걸어 버림

### Optimistic Lock
#### 실제로 Lock 을 사용하지 않고 Version 을 사용하여 데이터 정합성을 맞추어

### Named Lock
#### 메타 데이터로 Lock 을 걸어버림 
#### 이름을 가진 Lock 을 획득 후 다른 세션은 해당 자원에 접근이 불가, 해제를 수동으로 해주어야 함

### Redisson 을 활용 한 Pub / Sub 을 구현
#### 테스트 시 2개의 redis-cli 커맨더가 필요 (1개는 Subscribe 용 / 1 개는 Publish 용)

#### 1. 특정 채널을 Subscribe subscribe ch1
#### 2. 특정 채널에 Publish 메세지 전송 publish ch1 Hello

### 재시도가 필요한 케이스는 주로 Redisson 을 사용 (Redis 에 부하가 덜 가게 됨)
### 재시도가 필요하지 않은 케이스는 보통 Redis 를 사용 (재시도가 필요하지 않은 Lock 은 Lettuce 를 사용 !)
