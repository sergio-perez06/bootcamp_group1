package com.mercadolibre.fernandez_federico.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.CountryDealerStockResponseDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;

import com.mercadolibre.fernandez_federico.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.fernandez_federico.dtos.responses.SubsidiaryOrdersByDeliveryStatusDTO;
import com.mercadolibre.fernandez_federico.models.CountryDealer;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parts")
public class PartController {

    private final IStockWarehouseService stockService;

    @Autowired
    private TokenUtils tokenUtils;

    public PartController(IStockWarehouseService stockService)
    {
        this.stockService=stockService;
    }

    @GetMapping("/list")
    public List<PartDTO> getList(@RequestParam HashMap<String, String> params) throws Exception {
        return stockService.getParts(params);

    }

    // REQUERIMIENTO 2
    @GetMapping("/orders")
    public SubsidiaryOrdersByDeliveryStatusDTO getSubsidiaryOrdersByDeliveryStatus(
            @RequestParam String subsidiaryNumber,
            @RequestParam(required = false) String deliveryStatus,
            @RequestParam(required = false) String order,
            @RequestHeader("Authorization") String token) {

        Map<String,Object> claims = tokenUtils.getAllClaimsFromToken(token);

        if (subsidiaryNumber.length() != 4) {
            // exception
        } else {
            return stockService.getSubsidiaryOrdersByDeliveryStatus(subsidiaryNumber, claims.get("country").toString(), deliveryStatus,order);
        }

        return null;
    }

    @GetMapping("/allCountryDealers")
    public List<CountryDealer> countryDealers(){
        return stockService.getAllCountryDealers();
    }

    @PostMapping("/parts")
    public CountryDealerStockResponseDTO addPartCountryDealerStock(CountryDealerStockDTO countryDealerStock,
                                                                   @RequestHeader("Authorization") String token){
        Map<String,Object> claims = tokenUtils.getAllClaimsFromToken(token);

        return stockService.addStockToCountryDealer(countryDealerStock,claims.get("country").toString());
    }

}