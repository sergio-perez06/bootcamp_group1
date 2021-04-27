package com.mercadolibre.fernandez_federico.repositories;
import com.mercadolibre.fernandez_federico.models.StockWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IStockWarehouseRepository extends JpaRepository<StockWarehouse, Long>
{
}
