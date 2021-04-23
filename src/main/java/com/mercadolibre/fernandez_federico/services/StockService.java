package com.mercadolibre.fernandez_federico.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Comparator;
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
            List<PartDTO> partsDTO = new ArrayList<>();
            if (filters.isEmpty() || (filters.get("queryType").equals('C'))) {
                 partsDTO = partRepository.findAll()
                        .stream()
                        .map(parte -> modelMapper.map(parte, PartDTO.class))
                        .collect(Collectors.toList());
                return partsDTO;
            }
            if (filters.containsKey("queryType") && (filters.get("queryType").equals('C'))) {
                partsDTO = partRepository.findAll()
                        .stream()
                        .map(parte -> modelMapper.map(parte, PartDTO.class))
                        .collect(Collectors.toList());
                return partsDTO;
            }if (filters.containsKey("queryType") && (filters.get("queryType").equals('P') && filters.containsKey("date")))
            {
                LocalDate d1 = LocalDate.parse(filters.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                partsDTO = partRepository.findByLastUpdateBetween(d1, LocalDate.now())
                        .stream()
                        .map(parte -> modelMapper.map(parte, PartDTO.class))
                        .collect(Collectors.toList());
                return partsDTO;
            } if (filters.containsKey("queryType") && (filters.get("queryType").equals('V') && filters.containsKey("date"))){
                LocalDate d1 = LocalDate.parse(filters.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                partsDTO = partRepository.findByLastUpdateBetween(d1, LocalDate.now())
                        .stream()
                        .map(parte -> modelMapper.map(parte, PartDTO.class))
                        .collect(Collectors.toList());
                return partsDTO;
            } if(filters.containsKey("order")){
                if(filters.get("order").equals("1")){
                    partsDTO.sort(Comparator.comparing(PartDTO::getDescription));}
                if(filters.get("order").equals("2")){
                    partsDTO.sort(Comparator.comparing(PartDTO::getDescription).reversed());}
                if(filters.get("order").equals("3")){
                    partsDTO.sort(Comparator.comparing(PartDTO::getLastModification));}
            }

        }
        return null;
    }

}
