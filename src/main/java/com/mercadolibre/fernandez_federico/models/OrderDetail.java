package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrderDetail;

    private String accountType;

    private Integer quantity;

    private String reason;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idOrder", nullable = false)
    private Order order;
}
