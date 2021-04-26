package com.mercadolibre.fernandez_federico.models;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class CountryDealerPartPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "idPart", referencedColumnName = "id", nullable = false)
    private Part part;

    @ManyToOne
    @JoinColumn(name = "idCountryDealer", referencedColumnName = "id", nullable = false)
    private CountryDealer countryDealer;
}