package com.mercadolibre.fernandez_federico.unit.services.impl;

import com.mercadolibre.fernandez_federico.dtos.responses.CountryDealerStockResponseDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.CountryDealer;
import com.mercadolibre.fernandez_federico.models.Record;
import com.mercadolibre.fernandez_federico.models.StockWarehouse;
import com.mercadolibre.fernandez_federico.repositories.ICountryDealerRepository;
import com.mercadolibre.fernandez_federico.repositories.ISubsidiaryRepository;
import com.mercadolibre.fernandez_federico.services.ICountryDealerService;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;
import com.mercadolibre.fernandez_federico.services.impl.CountryDealerService;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class CountryDealerServiceTest {

    @Mock
    private ICountryDealerRepository countryDealerRepository;

    private ICountryDealerService countryDealerService;

    public CountryDealerServiceTest() {
        openMocks(this);
        countryDealerService = new CountryDealerService(countryDealerRepository);
    }

    @Test
    @DisplayName("Test invalido, no existe countryDealer con nombre dado")
    public void findByCountryInvalidNotExistentCountry() throws Exception {
        when(countryDealerRepository.findByCountry(anyString())).thenReturn(null);

        assertThatThrownBy(() -> {
            CountryDealer cd = countryDealerService.findByCountry("Hola");
        }).isInstanceOf(ApiException.class).hasMessageContaining("No existe country con el nombre 'Hola'");
    }

    @Test
    @DisplayName("Test invalido, no existe countryDealer con nombre dado")
    public void findByCountryValidExistentCountry() throws Exception {
        CountryDealer cdExpected = new CountryDealer();
        cdExpected.setCountry("Argentina");
        when(countryDealerRepository.findByCountry(anyString())).thenReturn(cdExpected);

        CountryDealer cd = countryDealerService.findByCountry("Argentina");

        assertThat(cd.getCountry()).isEqualTo(cdExpected.getCountry());
    }

}