package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name="stock_dealer")
@IdClass(PartPk.class)
public class StockDealer {
    @Id
    private Part part;

    @Column(nullable = false)
    @NotNull(message = "El tamaño no puede ser nulo")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="idCountryDealer", referencedColumnName = "id", nullable = false)
    private CountryDealer countryDealer;
}
