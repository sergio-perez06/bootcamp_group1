package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Table(name = "subsidiary")
@Entity
@Getter
@Setter
public class Subsidiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSubsidiary;


    @Column(nullable = false)
    @NotNull(message = "name no puede ser Nulo.")
    @Size(min = 2, max=100, message = "name debe tener entre 2 y 100 caracteres.")
    @Pattern(regexp="^[a-zA-Z]+$",message="name debe tener letras.")
    private String name;


    //private List<Order> orders;
    @OneToMany(mappedBy = "subsidiary")
    private List<StockSubsidiary> stockSubsidiaries;

    @ManyToOne
    @JoinColumn(name="idCountryDealer", nullable = false)
    private CountryDealer countryDealer;

    @OneToMany(mappedBy = "subsidiary")
    private List<Bill> bills;
}