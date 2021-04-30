package com.mercadolibre.fernandez_federico.services.impl;

import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.CountryDealer;
import com.mercadolibre.fernandez_federico.repositories.ICountryDealerRepository;
import com.mercadolibre.fernandez_federico.services.ICountryDealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CountryDealerService implements ICountryDealerService {

    private final ICountryDealerRepository countryDealerRepository;

    @Override
    public CountryDealer findByCountry(String country) {
        CountryDealer cd = countryDealerRepository.findByCountry(country);
        if (cd == null) {
            throw new ApiException(NOT_FOUND.name(), String.format("No existe country con el nombre '%s'", country), NOT_FOUND.value());
        }
        return cd;
    }
}
