package com.mercadolibre.fernandez_federico.models;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Embeddable
public class PartPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "idPart", referencedColumnName = "id", nullable = false)
    private Part part;
}