package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.Maker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMakerRepository extends JpaRepository<Maker,Long> {
}
