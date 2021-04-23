package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String orderNumber;


    // preguntar si este numero se contstruye al momento de dar respuesta o si se almacena
    @Column(nullable = false,length = 4)
    @NotNull(message = "subsidiaryNumber no puede ser Nulo.")
    @Size(min = 4, max=4, message = "subsidiaryNumber debe tener 4 caracteres.")
    private Integer CMorderNumber;

    @Column(nullable = false)
    @NotNull(message = "Fecha de creación no puede ser Nula")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM")
    private LocalDate orderDate;

    @Column(nullable = false)
    @NotNull(message = "Fecha de envío no puede ser Nula")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM")
    private LocalDate deliveryDate;

    @NotNull
    private Integer daysDelay;

    @NotNull
    @Column(length = 1)
    @Enumerated(EnumType.STRING)
    private OrderStatus deliveryStatus;

    @OneToMany(mappedBy="bill")
    private List<BillDetail> billDetails;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSubsidiary", referencedColumnName = "id", nullable = false)
    private Subsidiary subsidiary;
}