package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.Stock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IStockRepository extends JpaRepository<Stock, Long>
{ }
