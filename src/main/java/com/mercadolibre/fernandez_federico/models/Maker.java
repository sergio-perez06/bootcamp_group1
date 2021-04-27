package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="maker")
public class Maker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "maker no puede ser Nulo")
    @Size(min = 2, max = 100, message = "maker debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "maker debe tener caracteres alfanumericos")
    private String name;

    @JsonManagedReference
    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "maker")
    private List<Part> parts;
}
