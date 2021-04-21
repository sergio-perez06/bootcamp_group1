package com.mercadolibre.fernandez_federico.models;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class DiscountType {
    @Column(nullable = false)
    @NotNull(message = "type no puede ser nulo")
    private String type;

    @Column(nullable = false)
    @NotNull(message = "rate no puede ser nulo")
    private Integer rate;
}
