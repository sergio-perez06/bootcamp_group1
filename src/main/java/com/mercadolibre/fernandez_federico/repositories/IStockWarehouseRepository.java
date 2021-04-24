package com.mercadolibre.fernandez_federico.repositories;
import com.mercadolibre.fernandez_federico.models.StockWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IStockWarehouseRepository extends JpaRepository<StockWarehouse, Long>
{

}
