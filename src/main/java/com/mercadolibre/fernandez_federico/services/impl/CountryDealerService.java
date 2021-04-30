package com.mercadolibre.fernandez_federico.services.impl;

import com.mercadolibre.fernandez_federico.models.CountryDealer;
import com.mercadolibre.fernandez_federico.repositories.ICountryDealerRepository;
import com.mercadolibre.fernandez_federico.services.ICountryDealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryDealerService implements ICountryDealerService {

    private final ICountryDealerRepository countryDealerRepository;

    @Override
    public CountryDealer findByCountry(String country) {
        return countryDealerRepository.findByCountry(country);
    }
}
