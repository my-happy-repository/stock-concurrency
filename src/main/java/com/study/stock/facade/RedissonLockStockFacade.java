package com.study.stock.facade;

import com.study.stock.service.StockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonLockStockFacade {

    // Redisson 은 Pub / Sub 기반 이므로 Redis 에 부하를 줄여줄 수 있다는 장점이 있음
    // 다만 구현이 복잡하고, 라이브러리를 추가해주어야 하는 번거로움이 있음

    private RedissonClient redissonClient;

    private StockService stockService;

    public RedissonLockStockFacade(RedissonClient redissonClient, StockService stockService) {
        this.redissonClient = redissonClient;
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
//         Lock 객체를 가져 옴
        RLock lock = redissonClient.getLock(id.toString());

        try {
            // Lock 획득 실패 시 어떻게 처리할 건지 설정을 함
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                System.out.println("Lock 획득 실패");
                return;
            }

            stockService.decrease(id, quantity);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
