package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name="stock_dealer")
public class StockDealer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idStockDealer;
    @NotNull(message = "La cantidad no puede ser nula")
    private Integer quantity;
    //@NotNull(message = "El stock mínimo no puede ser nula")
    private Integer minStock;

    @ManyToOne
    @JoinColumn(name="idCountryDealer", nullable = false)
    private CountryDealer countryDealer;
}
