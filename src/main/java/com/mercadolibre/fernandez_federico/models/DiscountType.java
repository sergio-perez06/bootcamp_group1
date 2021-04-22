package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="discount_type")
public class DiscountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "type no puede ser nulo")
    private String type;

    @Column(nullable = false)
    @NotNull(message = "rate no puede ser nulo")
    private Integer rate;

}
