package com.mercadolibre.fernandez_federico.models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="part")
public class Part
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPart;

    @Column(nullable = false, length = 8)
    @NotNull(message = "partCode no puede ser Nulo")
    @Size(min = 8, max=8, message = "partCode debe tener ocho caracteres")
    @Pattern(regexp="^[0-9]{8}$",message="partCode debe poseer 8 caracteres numericos")
    private Integer partCode;

    @Column(nullable = false)
    @NotNull(message = "description no puede ser Nula")
    @Size(min = 2, max=100, message = "description debe tener entre 2 y 100 caracteres")
    @Pattern(regexp="^[a-zA-Z0-9 ]+$",message="description debe tener caracteres alfanumericos")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "maker no puede ser Nulo")
    @Size(min = 2, max=100, message = "maker debe tener entre 2 y 100 caracteres")
    @Pattern(regexp="^[a-zA-Z0-9 ]+$",message="maker debe tener caracteres alfanumericos")
    private String maker;

/*    @Column(nullable = false, length = 8)
    @NotNull(message = "La cantidad no puede ser Nulo")
    @Size(min = 8, max=8, message = "La cantidad debe tener ocho caracteres")
    @Pattern(regexp="^[0-9]{8}$",message="La cantidad debe poseer 8 caracteres numericos")
    private Integer quantity;*/

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
    @NotNull(message = "netWeight no puede ser Nulo")
    @Size(min = 1, max=5, message = "netWeight debe tener 5 caracteres")
    @Pattern(regexp="^(\\d{1,5})+$",message= "netWeight debe tener 5 caracteres numericos")
    private Integer netWeight;

    @Column(nullable = false)
    @NotNull(message = "longDimension no puede ser Nulo")
    @Size(min = 1, max=4, message = "longDimension debe tener 4 caracteres")
    @Pattern(regexp="^(\\d{1,4})+$",message= "longDimension debe tener 4 caracteres numericos")
    private Integer longDimension;

    @Column(nullable = false)
    @NotNull(message = "widthDimension no puede ser Nulo")
    @Size(min = 1, max=4, message = "widthDimension debe tener 4 caracteres")
    @Pattern(regexp="^(\\d{1,4})+$",message= "widthDimension debe tener 4 caracteres numericos")
    private Integer widthDimension;

    @Column(nullable = false)
    @NotNull(message = "tallDimension no puede ser Nulo")
    @Size(min = 1, max=4, message = "tallDimension debe tener 4 caracteres")
    @Pattern(regexp="^(\\d{1,4})+$",message= "tallDimension debe tener 4 caracteres numericos")
    private Integer tallDimension;

    @Column(nullable = false)
    @NotNull(message = "lastModification no puede ser Nulo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastModification;
}
