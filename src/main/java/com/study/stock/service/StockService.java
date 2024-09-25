package com.study.stock.service;

import com.study.stock.domain.Stock;
import com.study.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void decrease(Long id, Long quantity) {
        // 1. Stock 을 조회
        Stock stock = stockRepository.findById(id).orElseThrow();

        // 2. 재고를 감소
        stock.decrease(quantity);

        // 3. 갱신 된 값을 저장
        stockRepository.saveAndFlush(stock);
    }
}

