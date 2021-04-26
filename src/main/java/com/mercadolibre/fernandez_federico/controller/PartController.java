package com.mercadolibre.fernandez_federico.controller;

import java.util.HashMap;
import java.util.List;

import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;

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

    public PartController(IStockWarehouseService stockService)
    {
        this.stockService=stockService;
    }

    @GetMapping("/list")
    public List<PartDTO> getList(@RequestParam HashMap<String, String> params) throws Exception {
        return stockService.getParts(params);

    }

    // REQUERIMIENTO 2
    @GetMapping("/orders/{dealerNumber}")
    public SubsidiaryOrdersByDeliveryStatusDTO getSubsidiaryOrdersByDeliveryStatus(
            @PathVariable String dealerNumber,
            @RequestParam String subsidiaryNumber,
            @RequestParam(required = false) String deliveryStatus) {

        if(subsidiaryNumber.length() != 4) {
            // exception
        } else {
            return stockService.getSubsidiaryOrdersByDeliveryStatus(subsidiaryNumber, dealerNumber, deliveryStatus);
        }

        return null;
    }

    @GetMapping("/allCountryDealers")
    public List<CountryDealer> countryDealers(){
        return stockService.getAllCountryDealers();
    }



}