package com.mercadolibre.fernandez_federico.models;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser
@Table(name="application_user")
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApplicationUser;

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
    @JoinColumn(name = "idRole", referencedColumnName = "id", nullable = false)
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idCountryDealer", referencedColumnName = "id", nullable = false)
    private CountryDealer countryDealer;
}