package com.mercadolibre.fernandez_federico.models;

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
    private Long idBillDetail;

    private String accountType;

    private Integer quantity;

    private String reason;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idBill", referencedColumnName = "idBill", nullable = false)
    private Bill bill;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPart", nullable = false)
    private Part part;

}
