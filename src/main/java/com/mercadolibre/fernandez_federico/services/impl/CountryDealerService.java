package com.mercadolibre.fernandez_federico.services.impl;

import com.mercadolibre.fernandez_federico.models.CountryDealer;
import com.mercadolibre.fernandez_federico.repositories.ICountryDealerRepository;
import com.mercadolibre.fernandez_federico.services.ICountryDealerService;
import org.springframework.stereotype.Service;

@Service
public class CountryDealerService implements ICountryDealerService {

    private ICountryDealerRepository countryDealerRepository;

    CountryDealerService (ICountryDealerRepository countryDealerRepository){
        this.countryDealerRepository = countryDealerRepository;
    }

    @Override
    public CountryDealer findByCountry(String country) {
        return countryDealerRepository.findByCountry(country);
    }
}
