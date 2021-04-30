package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="bill")
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Bill {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NonNull
    @Size(min = 8, max = 8, message = "")
    @Column(name = "order_number")
    private String orderNumber;

    @Column(nullable = false, name = "cm_order_number", unique = true)
    @NotNull(message = "CMOrderNumber no puede ser Nulo.")
    @NonNull
    @Size(message = "CMOrderNumber debe tener 16 caracteres.")
    private String cmOrdernumberWarehouse;

    @Column(nullable = false)
    @NotNull(message = "Fecha de creación no puede ser Nula")
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate orderDate;

    @Column(nullable = false)
    @NotNull(message = "Fecha de envío no puede ser Nula")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM")
    @NonNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate deliveryDate;

    private Integer daysDelayed;

    @NotNull
    @NonNull
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private OrderStatus deliveryStatus;

    @JsonManagedReference
    @OneToMany(mappedBy="bill", cascade = CascadeType.ALL)
    private List<BillDetail> billDetails;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSubsidiary", referencedColumnName = "id", nullable = false)
    private Subsidiary subsidiary;


}