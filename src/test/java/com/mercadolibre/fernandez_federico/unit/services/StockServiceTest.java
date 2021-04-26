package com.mercadolibre.fernandez_federico.unit.services;

import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;
import com.mercadolibre.fernandez_federico.repositories.IStockWarehouseRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    IStockWarehouseRepository stockRepository;


    @Mock
    ModelMapper modelMapper;

    IStockWarehouseService stockService;


//    @BeforeEach
//    public void initialize() {
//        stockService = new StockWarehouseWarehouseService(stockRepository, partRepository, modelMapper);
//    }
 //   @BeforeEach
 //   public void initialize() {

}
