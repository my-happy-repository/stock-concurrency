package com.study.stock.transaction;

import com.study.stock.service.StockService;

// StockService 에 Transactional 어노테이션을 사용 시 예제로 생성 되는 클래스 ! Transaction 으로 클래스를 감 쌈 !
public class TransactionStockService {

    private StockService stockService;

    public TransactionStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) {
        startTransaction();

        // Transaction 으로 감싸도 테스트는 실패함 이유는 endTransaction 을 하기전에 다른 스레드가 decrease 를 해버릴 수 있음
        stockService.decrease(id, quantity);

        endTransaction();
    }

    private void startTransaction() {
        System.out.println("Transaction Start !!");
    }

    private void endTransaction() {
        System.out.println("Commit !!");
    }
}
