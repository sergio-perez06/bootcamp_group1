package com.mercadolibre.fernandez_federico.models;

import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import com.mercadolibre.fernandez_federico.util.enums.PartStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

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
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private String reason;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private PartStatus partStatus;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idBill", referencedColumnName = "id", nullable = false)
    private Bill bill;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPart", referencedColumnName = "id", nullable = false)
    private Part part;

}
