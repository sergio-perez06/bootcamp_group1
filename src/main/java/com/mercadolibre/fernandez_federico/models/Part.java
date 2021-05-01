package com.mercadolibre.fernandez_federico.models;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@Getter @Setter
@Entity
@Table(name="part")
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Part
{
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 8)
    @NotNull(message = "partCode no puede ser Nulo")
    @Size(min = 8, max = 8, message = "partCode debe tener ocho caracteres")
    @Pattern(regexp = "^[0-9]{8}$", message = "partCode debe poseer 8 caracteres numericos")
    private String partCode;

    @NonNull
    @Column(nullable = false)
    @NotNull(message = "description no puede ser Nula")
    @Size(min = 2, max=100, message = "description debe tener entre 2 y 100 caracteres")
    @Pattern(regexp="^[a-zA-Z0-9 ]+$",message="description debe tener caracteres alfanumericos")
    private String description;

    @NonNull
    @Column(nullable = false)
    @NotNull(message = "netWeight no puede ser Nulo")
    @Size(min = 1, max=5, message = "netWeight debe tener 5 caracteres")
    @Pattern(regexp="^(\\d{1,5})+$",message= "netWeight debe tener 5 caracteres numericos")
    private Integer netWeight;

    @NonNull
    @Column(nullable = false)
    @NotNull(message = "longDimension no puede ser Nulo")
    @Size(min = 1, max=4, message = "longDimension debe tener 4 caracteres")
    @Pattern(regexp="^(\\d{1,4})+$",message= "longDimension debe tener 4 caracteres numericos")
    private Integer longDimension;

    @NonNull
    @Column(nullable = false)
    @NotNull(message = "widthDimension no puede ser Nulo")
    @Size(min = 1, max=4, message = "widthDimension debe tener 4 caracteres")
    @Pattern(regexp="^(\\d{1,4})+$",message= "widthDimension debe tener 4 caracteres numericos")
    private Integer widthDimension;

    @NonNull
    @Column(nullable = false)
    @NotNull(message = "tallDimension no puede ser Nulo")
    @Size(min = 1, max=4, message = "tallDimension debe tener 4 caracteres")
    @Pattern(regexp="^(\\d{1,4})+$",message= "tallDimension debe tener 4 caracteres numericos")
    private Integer tallDimension;

    @NonNull
    @JsonBackReference(value="part-maker")
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idMaker", referencedColumnName = "id", nullable = false)
    private Maker maker;

    @NotNull
    @JsonManagedReference(value = "record-part" )
    @OneToMany(mappedBy = "part")
    private List<Record> records;
}
