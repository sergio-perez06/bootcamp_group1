package com.mercadolibre.fernandez_federico.models;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
public class PartPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "idPart", referencedColumnName = "idPart", nullable = false)
    private Part idPart;
}