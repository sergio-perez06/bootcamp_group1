package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecordRepository extends JpaRepository<Record,Long> {
}
