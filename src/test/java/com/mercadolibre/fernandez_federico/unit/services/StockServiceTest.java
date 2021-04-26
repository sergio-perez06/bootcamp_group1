package com.mercadolibre.fernandez_federico.unit.services;

import com.mercadolibre.fernandez_federico.services.IStockService;
import com.mercadolibre.fernandez_federico.repositories.IPartRepository;
import com.mercadolibre.fernandez_federico.repositories.IStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    IStockRepository stockRepository;

    @Mock
    IPartRepository partRepository;

    @Mock
    ModelMapper modelMapper;

    IStockService stockService;

    @BeforeEach
    public void initialize() {
        //stockService = new StockService(stockRepository, partRepository, modelMapper);
    }
}
