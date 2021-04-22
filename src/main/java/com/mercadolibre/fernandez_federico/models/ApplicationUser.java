package com.mercadolibre.fernandez_federico.models;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

import java.util.List;

@Getter @Setter
@Entity
@Table(name="applicationUser")
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(nullable = false, length = 12)
    @NotNull(message = "username no puede ser nulo")
    @Size(min = 5, max = 12, message = "username debe tener entre cinco y doce caracteres")
    @Pattern(regexp = "^[a-zA-Z\\d]{5,12}", message = "username debe tener entre cinco y doce caracteres alfanumericos")
    private String username;

    @Column(nullable = false, length = 100)
    @NotNull(message = "username no puede ser nulo")
    @Size(min = 5, max = 100, message = "username debe tener entre cinco y doce caracteres")
    @Pattern(regexp = "^[a-zA-Z\\d]{5,12}", message = "username debe tener entre cinco y doce caracteres alfanumericos")
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idRole",nullable = false)
    private Role role;

}