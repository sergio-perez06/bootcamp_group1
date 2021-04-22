package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecord;

    @Column(nullable = false)
    @NotNull(message = "normalPrice no puede ser Nulo")
    @Size(min = 4, max=9, message = "normalPrice debe tener 3 caracteres")
    @Pattern(regexp="^(\\d{1,6})+(\\.\\d{1,2})$",message= "normalPrice debe tener 6 caracteres numericos y 2 decimales")
    private Double normalPrice;

    @Column(nullable = false)
    @NotNull(message = "urgentPrice no puede ser Nulo")
    @Size(min = 4, max=9, message = "urgentPrice debe tener 3 caracteres")
    @Pattern(regexp="^(\\d{1,6})+(\\.\\d{1,2})$",message= "urgentPrice debe tener 6 caracteres numericos y 2 decimales")
    private Double urgentPrice;

    @Column(nullable = false)
    @NotNull(message = "lastModification no puede ser Nulo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastModification;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPart", nullable = false)
    private Part part;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDiscountType", nullable = false)
    private DiscountType discountType;
}
