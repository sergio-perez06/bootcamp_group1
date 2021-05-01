package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="role")
@NoArgsConstructor
@RequiredArgsConstructor
public class Role {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @OneToMany(mappedBy = "role")
    @JsonManagedReference(value="user-role")
    private List<ApplicationUser> applicationUsers;

}
