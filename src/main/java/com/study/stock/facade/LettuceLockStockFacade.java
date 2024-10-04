package com.study.stock.facade;

import com.study.stock.repository.RedisLockRepository;
import com.study.stock.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class LettuceLockStockFacade {

    private final RedisLockRepository redisLockRepository;

    private final StockService stockService;

    public LettuceLockStockFacade(RedisLockRepository redisLockRepository, StockService stockService) {
        this.redisLockRepository = redisLockRepository;
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while(!redisLockRepository.lock(id)) {
            // 만약 Lock 을 획득 실패 시 100 밀리 텀을 두고 계속 트라이 함
            Thread.sleep(100);
        }

        try {
            stockService.decrease(id, quantity);
        } finally {
            // 만약 감소를 하였으면 unlock 메소드를 사용
            redisLockRepository.unLock(id);
        }
    }
}

