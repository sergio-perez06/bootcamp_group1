package com.mercadolibre.fernandez_federico.services;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import com.mercadolibre.fernandez_federico.dtos.responses.OrderStatusResponseDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.models.CountryDealer;

public interface IStockService {
    //public List<PartDTO> getParts();
    public List<PartDTO> getParts(HashMap<String, String> filters) throws ParseException, Exception;

    public OrderStatusResponseDTO getOrderStatus(String orderNumberCM);

    public List<CountryDealer> getAllCountryDealers ();

    }
