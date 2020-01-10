package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {
    List<Stock> findByReservedStockGreaterThan(int zero);

}
