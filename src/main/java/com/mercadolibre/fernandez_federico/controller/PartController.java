package com.mercadolibre.fernandez_federico.controller;

import java.util.HashMap;
import java.util.List;

import com.mercadolibre.fernandez_federico.dtos.responses.OrderStatusResponseDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.models.CountryDealer;
import com.mercadolibre.fernandez_federico.services.IStockService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parts")
public class PartController {

    private final IStockService stockService;

    public PartController(IStockService stockService)
    {
        this.stockService=stockService;
    }

    @GetMapping("/list")
    public List<PartDTO> getList(@RequestParam HashMap<String, String> params) throws Exception {
        return stockService.getParts(params);

    }


    @GetMapping("/orders")
    public OrderStatusResponseDTO orderStatus(@RequestParam String oderNumberCM){

        return stockService.getOrderStatus(oderNumberCM);


    }

    @GetMapping("/allCountryDealers")
    public List<CountryDealer> countryDealers(){

        return stockService.getAllCountryDealers();


    }



}