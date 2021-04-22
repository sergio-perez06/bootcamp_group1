package com.mercadolibre.fernandez_federico.models;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name="user")
@AllArgsConstructor
public class ApplicationUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

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

}