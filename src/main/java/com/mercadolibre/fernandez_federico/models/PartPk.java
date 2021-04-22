package com.mercadolibre.fernandez_federico.models;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
public class PartPk implements Serializable {
    @OneToOne
    @JoinColumn(name = "id_part", referencedColumnName = "idPart", nullable = false)
    private Long id;
}