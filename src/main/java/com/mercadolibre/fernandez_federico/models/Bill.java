package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 8, max = 8, message = "")
    @Column(name = "order_number")
    private String orderNumber;

    @Column(nullable = false, name = "cm_order_number")
    @NotNull(message = "CMOrderNumber no puede ser Nulo.")
    @Size(message = "CMOrderNumber debe tener 16 caracteres.")
    private String cmOrdernumberWarehouse;


    @Column(nullable = false)
    @NotNull(message = "Fecha de creación no puede ser Nula")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate orderDate;

    @Column(nullable = false)
    @NotNull(message = "Fecha de envío no puede ser Nula")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate deliveryDate;

    private Integer daysDelayed;

    @NotNull
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private OrderStatus deliveryStatus;

    @JsonManagedReference
    @OneToMany(mappedBy="bill")
    private List<BillDetail> billDetails;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSubsidiary", referencedColumnName = "id", nullable = false)
    private Subsidiary subsidiary;
}