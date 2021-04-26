package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDiscountTypeRepository extends JpaRepository<DiscountType,Long> {
}
