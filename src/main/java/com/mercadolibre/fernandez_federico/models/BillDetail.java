package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import com.mercadolibre.fernandez_federico.util.enums.PartStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="bill_detail")
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class BillDetail {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String description;

    @NonNull
    private Integer quantity;

    @NonNull
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @NonNull
    private String reason;

    @NonNull
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private PartStatus partStatus;

    @NonNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idBill", referencedColumnName = "id", nullable = false)
    private Bill bill;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPart", referencedColumnName = "id", nullable = false)
    private Part part;


}
