package com.nicat.task.expresstask.repository;

import com.nicat.task.expresstask.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    Stock findBySymbol(String symbol);
}
