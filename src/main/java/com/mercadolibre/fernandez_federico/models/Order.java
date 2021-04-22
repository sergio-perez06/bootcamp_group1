package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@OneToMany()
    *   private long
     * */


    @Column(nullable = false)
    @NotNull(message = "Fecha de creación no puede ser Nula")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM")
    private Date emitedDate;

}
