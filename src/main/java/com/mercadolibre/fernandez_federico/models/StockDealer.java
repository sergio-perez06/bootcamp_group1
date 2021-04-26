package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name="stock_dealer")
@IdClass(CountryDealerPartPk.class)
public class StockDealer {
    @Id
    private Part part;

    @Id
    private CountryDealer countryDealer;

    @Column(nullable = false)
    @NotNull(message = "El tamaño no puede ser nulo")
    private Integer quantity;
}