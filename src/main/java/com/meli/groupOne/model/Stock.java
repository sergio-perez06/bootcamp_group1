package com.meli.groupOne.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "STOCK")
@Entity
@Data
public class Stock {
    @Column(name = "idStock", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStock;

    private Integer quantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idPart", referencedColumnName = "idPart", nullable = false)
    private Part parts;
}