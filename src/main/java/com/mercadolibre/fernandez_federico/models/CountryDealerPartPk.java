package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class CountryDealerPartPk implements Serializable {
    @ManyToOne
    @JsonBackReference(value = "countrydealerpartpk-part")
    @JoinColumn(name = "idPart", referencedColumnName = "id", nullable = false)
    private Part part;

    @ManyToOne
    @JoinColumn(name = "idCountryDealer", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "countrydealerpartpk-countrydealer")
    private CountryDealer countryDealer;
}