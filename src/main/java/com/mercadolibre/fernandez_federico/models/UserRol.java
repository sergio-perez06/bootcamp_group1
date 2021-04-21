package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="stock")
public class UserRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUserRol;

}
