package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IPartRepository extends JpaRepository<Part,Long>
{
    @Query("select p.partCode, p.description, m.name, s.quantity, d.type, r.normalPrice, r.urgentPrice, p.netWeight, p.longDimension, p.widthDimension, p.tallDimension, r.lastModification " +
            "from Part p join Record r on p.id=r.part.id join Maker m on p.maker.id=m.id " +
            "join StockWarehouse s on p.id=s.part.id join DiscountType d on r.discountType.id=d.id " +
            "where r.lastModification between :lastUpdate and :nowDate")
    List<Part> findByLastUpdateBetween(@Param("lastUpdate") LocalDate lastUpdate, LocalDate nowDate);
    @Query("select p.partCode, p.description, m.name, s.quantity, d.type, r.normalPrice, r.urgentPrice, p.netWeight, p.longDimension, p.widthDimension, p.tallDimension, r.lastModification " +
            "from Part p join Record r on p.id=r.part.id join Maker m on p.maker.id=m.id " +
            "join StockWarehouse s on p.id=s.part.id join DiscountType d on r.discountType.id=d.id " +
            "where r.lastModification between :lastUpdate and :nowDate")
    List<Part> findByLastUpdatePriceBetween(@Param("lastUpdate") LocalDate lastUpdate, LocalDate nowDate);
}