package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IPartRepository extends JpaRepository<Part,Long>
{
    @Query("select p from Part p join Record r on p.id=r.part.id where r.lastModification between :lastUpdate and :nowDate")
    List<Part> findByLastUpdateBetween(@Param("lastUpdate") LocalDate lastUpdate, LocalDate nowDate);
}
//@Query("select p from Part p join Record r on p.idPart=r.part.idPart where r.lastModification between :publicationTimeStart and :publicationTimeEnd")
//"Select p from Part p"+" Join Record r on p.idPart=r.part.idPart"+" where Record.lastModification between :publicationTimeStart and :publicationTimeEnd"