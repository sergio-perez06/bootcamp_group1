package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter@Setter
@Entity
@Table(name="country_dealer")
public class CountryDealer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCountryDealer;

    @Column(nullable = false)
    @NotNull(message = "name no puede ser Nulo.")
    @Size(min = 2, max=100, message = "name debe tener entre 2 y 100 caracteres.")
    @Pattern(regexp="^[a-zA-Z]+$",message="name debe tener letras.")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "country no puede ser Nulo.")
    @Size(min = 2, max=100, message = "country debe tener entre 2 y 100 caracteres.")
    @Pattern(regexp="^[a-zA-Z]+$",message="country debe tener letras.")
    private String country;

    @OneToMany(mappedBy ="countryDealer" )
    private List<StockDealer> stockDealers;

}
