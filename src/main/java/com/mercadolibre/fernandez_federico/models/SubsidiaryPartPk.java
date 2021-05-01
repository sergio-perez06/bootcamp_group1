package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class SubsidiaryPartPk implements Serializable {
    @ManyToOne
    @JsonBackReference(value = "subsidiarypartpk-part")
    @JoinColumn(name = "idPart", referencedColumnName = "id", nullable = false)
    private Part part;

    @ManyToOne
    @JsonBackReference(value = "subsidiarypartpk-subsidiary")
    @JoinColumn(name = "idSubsidiary", referencedColumnName = "id", nullable = false)
    private Subsidiary subsidiary;
}