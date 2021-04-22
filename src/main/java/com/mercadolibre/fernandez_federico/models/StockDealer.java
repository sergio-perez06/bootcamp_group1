package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

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

    @Column(nullable = false)
    @NotNull(message = "El tamaño no puede ser nulo")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="idCountryDealer", nullable = false)
    private CountryDealer countryDealer;

    @Unique
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPart", nullable = false)
    private Part part;
}
