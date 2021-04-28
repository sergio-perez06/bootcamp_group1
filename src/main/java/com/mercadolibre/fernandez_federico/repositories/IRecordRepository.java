package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.Part;
import com.mercadolibre.fernandez_federico.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRecordRepository extends JpaRepository<Record, Long> {
    Record findTopByPartIdOrderByIdDesc(long idPart);
    Record findById(long id);
}
