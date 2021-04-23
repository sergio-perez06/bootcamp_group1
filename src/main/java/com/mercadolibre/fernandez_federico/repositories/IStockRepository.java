package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.Part;
import com.mercadolibre.fernandez_federico.models.StockWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IStockRepository extends JpaRepository<StockWarehouse, Long>
{  @Query("select s.part.partCode, s.part.description, m.name, s.quantity, d.type, r.normalPrice, r.urgentPrice, s.part.netWeight, s.part.longDimension, s.part.widthDimension, s.part.tallDimension, r.lastModification " +
        "from StockWarehouse s join Record r on s.part.id=r.part.id join Maker m on s.part.maker.id=m.id " +
        "join DiscountType d on r.discountType.id=d.id " +
        "where r.lastModification between :lastUpdate and :nowDate")
    List<StockWarehouse> findByLastUpdateBetween(@Param("lastUpdate") LocalDate lastUpdate, LocalDate nowDate);

}
