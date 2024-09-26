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

