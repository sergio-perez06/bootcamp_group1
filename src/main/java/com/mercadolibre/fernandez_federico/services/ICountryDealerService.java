package com.mercadolibre.fernandez_federico.services;

import com.mercadolibre.fernandez_federico.models.CountryDealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICountryDealerService  {
    CountryDealer findByCountry(String country);
}
