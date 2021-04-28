package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPartRepository extends JpaRepository<Part,Long> {

    Part findByPartCode(String partCode);

}
