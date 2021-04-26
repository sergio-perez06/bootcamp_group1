package com.mercadolibre.fernandez_federico.unit.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.models.Part;
import com.mercadolibre.fernandez_federico.models.StockDealer;
import com.mercadolibre.fernandez_federico.services.IStockService;
import com.mercadolibre.fernandez_federico.services.StockService;
import com.mercadolibre.fernandez_federico.repositories.IPartRepository;
import com.mercadolibre.fernandez_federico.repositories.IStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

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
