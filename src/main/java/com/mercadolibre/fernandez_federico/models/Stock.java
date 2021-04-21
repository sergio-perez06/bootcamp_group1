package com.mercadolibre.fernandez_federico.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.checkerframework.common.aliasing.qual.Unique;

@Getter @Setter
@Entity
@Table(name="stock")
public class Stock
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStock;

    private Integer quantity;

    @Unique
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idPart", referencedColumnName = "idPart", nullable = false)
    private Part part;
}
