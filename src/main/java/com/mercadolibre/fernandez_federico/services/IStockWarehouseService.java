package com.mercadolibre.fernandez_federico.services;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import com.mercadolibre.fernandez_federico.dtos.request.BillRequestDTO;
import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.*;
import com.mercadolibre.fernandez_federico.models.CountryDealer;

public interface IStockWarehouseService {
    List<PartDTO> getParts(HashMap<String, String> filters) throws ParseException, Exception;

    SubsidiaryOrdersByDeliveryStatusDTO getSubsidiaryOrdersByDeliveryStatus(String subsidiaryNumber, String countryName, String deliveryStatus, String order);

    CountryDealerStockResponseDTO addStockToCountryDealer(CountryDealerStockDTO countryDealerStock, String country);

    BillDTO addBillToCountryDealer(BillRequestDTO billRequestDTO, String countryName);
}
