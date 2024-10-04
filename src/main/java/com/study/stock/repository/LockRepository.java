package com.study.stock.repository;

import com.study.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// 실무 에서는 별도의 datasource 를 분리해서 사용 (connection pool 이 부족해 질 수 있음 !)
public interface LockRepository extends JpaRepository<Stock, Long> {

    @Query(value = "SELECT get_lock(:key, 3000)", nativeQuery = true)
    void getLock(String key);

    @Query(value = "SELECT release_lock(key)", nativeQuery = true)
    void releaseLock(String key);
}
