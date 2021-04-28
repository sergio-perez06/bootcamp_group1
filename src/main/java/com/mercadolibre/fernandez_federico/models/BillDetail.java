package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import com.mercadolibre.fernandez_federico.util.enums.PartStatus;
import com.mercadolibre.fernandez_federico.util.enums.converters.AccountTypeConverter;
import com.mercadolibre.fernandez_federico.util.enums.converters.PartStatusConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="bill_detail")
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Integer quantity;

    @Column(length = 50)
    @Convert(converter = AccountTypeConverter.class)
    private AccountType accountType;

    private String reason;

    @Column(length = 50)
    @Convert(converter = PartStatusConverter.class)
    private PartStatus partStatus;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idBill", referencedColumnName = "id", nullable = false)
    private Bill bill;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPart", referencedColumnName = "id", nullable = false)
    private Part part;

}
