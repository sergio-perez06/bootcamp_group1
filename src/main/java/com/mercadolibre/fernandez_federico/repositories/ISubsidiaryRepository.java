package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.Subsidiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubsidiaryRepository extends JpaRepository<Subsidiary, Long> {

    List<Subsidiary> findAll();

    Subsidiary findBySubsidiaryNumber(Integer subsidiaryNumber);


}
