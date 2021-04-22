package com.mercadolibre.fernandez_federico.models;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name="user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(nullable = false, length = 12)
    @NotNull(message = "username no puede ser nulo")
    @Size(min = 5, max = 12, message = "username debe tener entre cinco y doce caracteres")
    @Pattern(regexp = "^[a-zA-Z\\d]{5,12}", message = "username debe tener entre cinco y doce caracteres alfanumericos")
    private String username;

    @Column(nullable = false, length = 12)
    @NotNull(message = "username no puede ser nulo")
    @Size(min = 5, max = 12, message = "username debe tener entre cinco y doce caracteres")
    @Pattern(regexp = "^[a-zA-Z\\d]{5,12}", message = "username debe tener entre cinco y doce caracteres alfanumericos")
    private String password;

//    @OneToMany(mappedBy = "user")
//    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name="idUserRol", nullable = false)
    private UserRole roles;
}