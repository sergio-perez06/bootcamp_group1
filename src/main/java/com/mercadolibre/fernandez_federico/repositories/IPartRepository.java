package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IPartRepository extends JpaRepository<Part,Long>
{
    @Query("select p from Part p inner join Record r on p.idPart=r.part.idPart where r.lastModification between: publicationTimeStart and: publicationTimeEnd")
    List<Part> findDate(@Param("publicationTimeStart") Date publicationTimeStart, @Param("publicationTimeEnd")
            Date publicationTimeEnd);
}
