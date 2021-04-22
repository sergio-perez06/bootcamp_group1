package com.mercadolibre.fernandez_federico.models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Getter @Setter
@Entity
@Table(name="part")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Part
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPart;

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

 //   @NotNull
 //   @ManyToOne(cascade = CascadeType.ALL)
 //   @JoinColumn(name="idMaker", nullable = false)
 //   private Maker maker;

    @NotNull
    @OneToMany(mappedBy = "part")
    private List<Record> records;
}
