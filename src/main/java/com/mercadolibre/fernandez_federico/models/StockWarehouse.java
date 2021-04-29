package com.mercadolibre.fernandez_federico.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter @Setter
@Entity
@Table(name = "stock_warehouse")
@IdClass(PartPk.class)
@AllArgsConstructor
@NoArgsConstructor
public class StockWarehouse {
    @Id
    private Part part;

    @Column(nullable = false)
    @NotNull(message = "El tamaño no puede ser nulo")
    private Integer quantity;


}