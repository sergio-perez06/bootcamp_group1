package com.mercadolibre.fernandez_federico.services;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.SubsidiaryOrdersByDeliveryStatusDTO;
import com.mercadolibre.fernandez_federico.models.CountryDealer;

public interface IStockWarehouseService {
    //public List<PartDTO> getParts();
    public List<PartDTO> getParts(HashMap<String, String> filters) throws ParseException, Exception;

    public void getOrderStatus(String orderNumberCM);

    public List<CountryDealer> getAllCountryDealers ();

    SubsidiaryOrdersByDeliveryStatusDTO getSubsidiaryOrdersByDeliveryStatus(String dealerNumber);
}
