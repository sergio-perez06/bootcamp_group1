package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "name no puede ser Nulo.")
    @Size(min = 2, max = 100, message = "name debe tener entre 2 y 100 caracteres.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "name debe tener letras.")
    private String name;

    @OneToMany(mappedBy = "subsidiary")
    @JsonManagedReference(value = "stocksubsidiaries-subsidiary")
    private List<StockSubsidiary> stockSubsidiaries;

    @Column(nullable = false,length = 4)
    @NotNull(message = "subsidiaryNumber no puede ser Nulo.")
    @Size(min = 4, max = 4, message = "subsidiaryNumber debe tener 4 caracteres.")
    private String subsidiaryNumber;

    @ManyToOne
    @JsonBackReference(value="subsidiary-countrydealer")
    @JoinColumn(name="idCountryDealer", referencedColumnName = "id", nullable = false)
    private CountryDealer countryDealer;

    @JsonManagedReference(value="bill-subsidiary")
    @OneToMany(mappedBy = "subsidiary", cascade = CascadeType.ALL)
    private List<Bill> bills;
}