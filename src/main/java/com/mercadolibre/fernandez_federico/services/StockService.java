package com.mercadolibre.fernandez_federico.services;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.repositories.IPartRepository;
import com.mercadolibre.fernandez_federico.repositories.IStockRepository;
import org.modelmapper.ModelMapper;


import org.springframework.stereotype.Service;

@Service
public class StockService implements IStockService {
    
    private IPartRepository partRepository;
    private final ModelMapper modelMapper;

    public StockService(IPartRepository partRepository, ModelMapper modelMapper)
    {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PartDTO> getParts(HashMap<String, String> filters)
    {
        if (filters.isEmpty())
        {
            List<PartDTO> partsDTO = partRepository.findAll()
                    .stream()
                    .map(parte -> modelMapper.map(parte, PartDTO.class))
                    .collect(Collectors.toList());
            return partsDTO;
        }
        else
        return null;
    }

}
