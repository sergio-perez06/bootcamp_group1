package com.mercadolibre.fernandez_federico.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.SubsidiaryOrdersByDeliveryStatusDTO;
import com.mercadolibre.fernandez_federico.exceptions.ApiException;
import com.mercadolibre.fernandez_federico.models.CountryDealer;
import com.mercadolibre.fernandez_federico.models.Subsidiary;
import com.mercadolibre.fernandez_federico.repositories.ICountryDealerRepository;
import com.mercadolibre.fernandez_federico.repositories.IPartRepository;
import com.mercadolibre.fernandez_federico.repositories.ISubsidiaryRepository;
import com.mercadolibre.fernandez_federico.services.IStockService;
import org.modelmapper.ModelMapper;


import org.springframework.stereotype.Service;

@Service
public class StockService implements IStockService {


    private IPartRepository partRepository;
    private ICountryDealerRepository countryDealerRepository;
    private ISubsidiaryRepository subsidiaryRepository;
    private final ModelMapper modelMapper;

    public StockService(
            IPartRepository partRepository,
            ModelMapper modelMapper,
            ICountryDealerRepository countryDealerRepository,
            ISubsidiaryRepository subsidiaryRepository)
    {
        this.partRepository = partRepository;
        this.countryDealerRepository = countryDealerRepository;
        this.subsidiaryRepository = subsidiaryRepository;

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


    // Requirement 3
    public void getOrderStatus(String orderNumberCM){


        String[] splitted = orderNumberCM.split("-");

        Integer subsidiaryNumber = Integer.parseInt(splitted[0]);
        Integer CountryDealerNumber = Integer.parseInt(splitted[1]);
        Integer orderNumber = Integer.parseInt(splitted[2]);


        CountryDealer countryDelaer = countryDealerRepository.findByDealerNumber(CountryDealerNumber);
        System.out.println(countryDelaer+"\n");



        Subsidiary subsidiary  = subsidiaryRepository.findBySubsidiaryNumber(subsidiaryNumber);

        System.out.println(subsidiary+"\n");




    }


    // Utilitary
    public List<CountryDealer> getAllCountryDealers (){

        System.out.println(countryDealerRepository.count());
        return countryDealerRepository.findAll();
    }

    //Requirement 2
    @Override
    public SubsidiaryOrdersByDeliveryStatusDTO getSubsidiaryOrdersByDeliveryStatus(String subsidiaryNumber) {
        SubsidiaryOrdersByDeliveryStatusDTO response = new SubsidiaryOrdersByDeliveryStatusDTO();

        Subsidiary s = subsidiaryRepository.findBySubsidiaryNumber(Integer.parseInt(subsidiaryNumber));

        if(s!= null){

        }
        return null;
    }



}
