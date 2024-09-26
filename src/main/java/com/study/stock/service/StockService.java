package com.study.stock.service;

import com.study.stock.domain.Stock;
import com.study.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // synchronized 키워드를 사용 시 1개의 스레드만 접근이 가능함 !
//    @Transactional  // Transactional Annotaion !
    // Synchronized 로는 해결이 힘듦 ! 서버 2대 이상일 시 작동이 정상적으로 되지 않음 !
    public synchronized void decrease(Long id, Long quantity) {
        // 1. Stock 을 조회
        Stock stock = stockRepository.findById(id).orElseThrow();

        // 2. 재고를 감소
        stock.decrease(quantity);

        // 3. 갱신 된 값을 저장
        stockRepository.saveAndFlush(stock);
    }
}

