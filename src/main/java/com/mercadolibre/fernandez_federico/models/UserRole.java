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
    private long idUserRol;
    private String name;
    @ManyToMany(mappedBy= "user_role")
    private List<User> users;

    //@ManyToMany
    //@JoinTable(name = "roles_privileges",
      //      joinColumns = @JoinColumn(
        //            name = "role_id", referencedColumnName = "id"),
          //  inverseJoinColumns = @JoinColumn(
            //        name = "privilege_id", referencedColumnName = "id")))

}
