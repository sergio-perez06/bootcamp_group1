package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}