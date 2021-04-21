package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="stock_dealer")
public class StockDealer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idStockDealer;
    private Integer quantity;
    private Integer minStock;

    @ManyToOne
    @JoinColumn(name="idCountryDealer", nullable = false)
    private CountryDealer countryDealer;
}
