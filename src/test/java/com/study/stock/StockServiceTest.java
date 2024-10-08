package com.study.stock;

import com.study.stock.domain.Stock;
import com.study.stock.facade.NamedLockStockFacade;
import com.study.stock.repository.StockRepository;
import com.study.stock.service.StockService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private NamedLockStockFacade namedLockStockFacade;

    @BeforeEach
    public void before() {
        stockRepository.saveAndFlush(new Stock(1L, 100L));
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    public void 재고감소() {
        stockService.decrease(1L, 1L);

        Stock stock = stockRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(99L, stock.getQuantity());
    }

    @Test
    public void 동시의_100개의_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    // perssimisticLock 을 사용 !
//                    pessimisticLockStockService.decrease(1L, 1L);
                    namedLockStockFacade.decrease(1L, 1L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        // 예상은 0개 이지만 실제 카운트는 0개가 아니게 됨 !
        // Race Condition 2개 이상의 스레드가 공유 되는 자원에 접근을 함
        // Race Condition 이 발생 하게 됨 공유 된 자원에 여러 스레드가 접근을 하게 됨 (DB 에서 값을 업데이트 하기 전에 값을 Select 를 함 !)
        Stock stock = stockRepository.findById(1L).orElseThrow();

        Assertions.assertEquals(0L, stock.getQuantity());
    }
}

