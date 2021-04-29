package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.DiscountType;
import com.mercadolibre.fernandez_federico.models.Maker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDiscountTypeRepository extends JpaRepository<DiscountType, Long> {
}
