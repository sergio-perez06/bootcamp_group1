package com.mercadolibre.fernandez_federico.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.repositories.IPartRepository;
import com.mercadolibre.fernandez_federico.repositories.IStockRepository;
import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.modelmapper.ModelMapper;
import java.util.Date;


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
    public List<PartDTO> getParts(HashMap<String, String> filters) throws Exception {
        if(partRepository.findAll().isEmpty())
            throw new ApiException("Not Found","La lista no existe",404 );
        else {
            if (filters.isEmpty() || (filters.get("queryType").equals('C'))) {
                List<PartDTO> partsDTO = partRepository.findAll()
                        .stream()
                        .map(parte -> modelMapper.map(parte, PartDTO.class))
                        .collect(Collectors.toList());
                return partsDTO;
            } else if (filters.containsKey("queryType") && filters.containsKey("date"))
            {
                LocalDate d1 = LocalDate.parse("2021-04-20", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate d2 = LocalDate.parse("2021-04-23", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                List<PartDTO> partsDTO = partRepository.findByLastUpdateBetween(d1,d2)
                        .stream()
                        .map(parte -> modelMapper.map(parte, PartDTO.class))
                        .collect(Collectors.toList());
                return partsDTO;
            }
            else
            {

            }
        }
        return null;
    }

}
