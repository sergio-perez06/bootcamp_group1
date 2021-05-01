package com.mercadolibre.fernandez_federico.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="stock_subsidiary")
@IdClass(SubsidiaryPartPk.class)
public class StockSubsidiary {
    @Id
    private Part part;

    @Id
    @JsonBackReference(value = "stocksubsidiaries-subsidiary")
    private Subsidiary subsidiary;

    @Column(nullable = false)
    @NotNull(message = "El tamaño no puede ser nulo")
    private Integer quantity;
}
