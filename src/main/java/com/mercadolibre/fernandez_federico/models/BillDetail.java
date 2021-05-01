package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class BillDetail {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String description;

    @NonNull
    private Integer quantity;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    @NonNull
    private AccountType accountType;

    @NonNull
    private String reason;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    @NonNull
    private PartStatus partStatus;

    @JsonBackReference(value="billDetails-bill")
    @ManyToOne
    @JoinColumn(name = "idBill", referencedColumnName = "id", nullable = false)
    @NonNull
    private Bill bill;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPart", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value="billDetails-part")
    private Part part;

}
