package com.mercadolibre.fernandez_federico.models;


import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="stock_subsidiary")
public class StockSubsidiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStockSubsidiary;

    @Unique
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPart", nullable = false)
    private Part part;

    @Unique
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idSubsidiary", nullable = false)
    private Subsidiary subsidiary;

    @Column(nullable = false)
    @NotNull(message = "El tamaño no puede ser nulo")
    private Integer quantity;

}
