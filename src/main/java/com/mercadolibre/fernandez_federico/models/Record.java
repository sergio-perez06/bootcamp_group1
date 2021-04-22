package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "discountType no puede ser Nulo")
    @Size(min = 3, max=3, message = "discountType debe tener 3 caracteres")
    @Pattern(regexp="^[a-zA-Z]{1}\\d{2}$",message="discountType debe tener 1 caracter alfabetico y 2 caracteres numericos")
    private String discountType;

    @Column(nullable = false)
    @NotNull(message = "normalPrice no puede ser Nulo")
    @Size(min = 4, max=9, message = "normalPrice debe tener 3 caracteres")
    @Pattern(regexp="^(\\d{1,6})+(\\.\\d{1,2})$",message= "normalPrice debe tener 6 caracteres numericos y 2 decimales")
    private Double normalPrice;

    @Column(nullable = false)
    @NotNull(message = "urgentPrice no puede ser Nulo")
    @Size(min = 4, max=9, message = "urgentPrice debe tener 3 caracteres")
    @Pattern(regexp="^(\\d{1,6})+(\\.\\d{1,2})$",message= "urgentPrice debe tener 6 caracteres numericos y 2 decimales")
    private Double urgentPrice;

    @Column(nullable = false)
    @NotNull(message = "lastModification no puede ser Nulo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastModification;
}
