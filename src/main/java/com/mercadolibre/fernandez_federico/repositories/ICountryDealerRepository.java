package com.mercadolibre.fernandez_federico.repositories;

import com.mercadolibre.fernandez_federico.models.CountryDealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICountryDealerRepository extends JpaRepository<CountryDealer, Long> {

    List<CountryDealer> findAll();

    CountryDealer findByDealerNumber(Integer dealerNumber);

    CountryDealer findByCountry(String country);
}
