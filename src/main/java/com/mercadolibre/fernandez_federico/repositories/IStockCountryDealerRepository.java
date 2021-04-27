package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.StockDealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStockCountryDealerRepository extends JpaRepository<StockDealer,Long> {

}
