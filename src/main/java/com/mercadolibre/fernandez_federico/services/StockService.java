package com.mercadolibre.fernandez_federico.services;

import java.util.HashMap;
import java.util.List;

import com.mercadolibre.fernandez_federico.config.ModelMapperConfig;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.repositories.IStockRepository;

import org.springframework.stereotype.Service;

@Service
public class StockService implements IStockService {
    
    private IStockRepository stockRepository;
    private ModelMapperConfig modelMapper;

    public StockService(
        IStockRepository stockRepository,
        ModelMapperConfig modelMapper
    ) {
        this.stockRepository = stockRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PartDTO> getParts(HashMap<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return modelMapper.mapList(
                stockRepository.findAll()
                , PartDTO.class);
        }
        return null;
    }

}
