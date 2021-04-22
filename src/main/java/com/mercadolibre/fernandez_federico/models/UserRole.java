package com.mercadolibre.fernandez_federico.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserRol;
    private String name;
    @OneToMany(mappedBy="roles")
    private List<User> users;

}
