package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="MAKER")
public class Maker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaker;

    @Column(nullable = false)
    @NotNull(message = "maker no puede ser Nulo")
    @Size(min = 2, max=100, message = "maker debe tener entre 2 y 100 caracteres")
    @Pattern(regexp="^[a-zA-Z0-9 ]+$",message="maker debe tener caracteres alfanumericos")
    private String name;

    @OneToMany(mappedBy = "maker")
    private List<PartPk> partpks;

}
