package com.mercadolibre.fernandez_federico.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mercadolibre.fernandez_federico.dtos.request.BillRequestDTO;
import com.mercadolibre.fernandez_federico.dtos.request.CountryDealerStockDTO;
import com.mercadolibre.fernandez_federico.dtos.responses.*;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;

import com.mercadolibre.fernandez_federico.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.fernandez_federico.models.CountryDealer;

import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/v1/parts")
@Validated
public class PartController extends GlobalExceptionHandler {

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

    // Requirement 2
    @GetMapping("/orders")
    public SubsidiaryOrdersByDeliveryStatusDTO getSubsidiaryOrdersByDeliveryStatus(
            @RequestParam String subsidiaryNumber,
            @RequestParam(required = false) String deliveryStatus,
            @RequestParam(required = false) String order,
            @RequestHeader("Authorization") String token) {
        Map<String,Object> claims = tokenUtils.getAllClaimsFromToken(token);

        return stockService.getSubsidiaryOrdersByDeliveryStatus(dealerNumber, claims.get("country").toString(), deliveryStatus,order);
    }

    // Requirement 4
    @PostMapping()
    public CountryDealerStockResponseDTO addPartCountryDealerStock(
            @RequestBody CountryDealerStockDTO countryDealerStock,
            @RequestHeader("Authorization") String token) {
        Map<String,Object> claims = tokenUtils.getAllClaimsFromToken(token);

        return stockService.addStockToCountryDealer(countryDealerStock, claims.get("country").toString());
    }

    // Requirement 5
    @PostMapping("/orders")
    public BillDTO addBillCountryDealer(
            @RequestBody BillRequestDTO billRequestDTO,
            @RequestHeader("Authorization") String token){
        Map<String,Object> claims = tokenUtils.getAllClaimsFromToken(token);

        return stockService.addBillToCountryDealer(billRequestDTO,claims.get("country").toString());
    }

    @GetMapping("/allCountryDealers")
    public List<CountryDealer> countryDealers(){
        return stockService.getAllCountryDealers();
    }
}