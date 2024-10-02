package com.study.stock.service;

import com.study.stock.domain.Stock;
import com.study.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class OptimisticLockStockService {

    private final StockRepository stockRepository;

    public OptimisticLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithOptimisticLock(id);

        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}
