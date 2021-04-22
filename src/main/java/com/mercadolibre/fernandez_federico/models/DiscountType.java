package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="discount_type")
public class DiscountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiscountType;

    @Column(nullable = false)
    @NotNull(message = "type no puede ser nulo")
    private String type;

    @Column(nullable = false)
    @NotNull(message = "rate no puede ser nulo")
    private Integer rate;

    @OneToMany(mappedBy = "discountType")
    private List<Record> records;
}
