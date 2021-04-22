package com.mercadolibre.fernandez_federico.models;


import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name="STOCKSUBSIDIARY")
public class StockSubsidiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStockSubsidiary;

    @Unique
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idPart", referencedColumnName = "idPart", nullable = false)
    private Part part;

    @Unique
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idSubsidiary", referencedColumnName = "ID_SUBSIDIARY", nullable = false)
    private Subsidiary subsidiary;

    @Column(nullable = false)
    @NotNull(message = "El tamaño no puede ser nulo")
    private Integer quantity;

}
