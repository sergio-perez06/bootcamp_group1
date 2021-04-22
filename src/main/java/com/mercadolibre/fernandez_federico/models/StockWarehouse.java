package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "stock_warehouse")
@IdClass(PartPk.class)
public class StockWarehouse {
    @Id
    private Part part;
}
