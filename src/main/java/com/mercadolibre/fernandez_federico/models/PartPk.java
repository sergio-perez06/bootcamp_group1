package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class PartPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "idPart", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "partpk-part" )
    private Part part;
}