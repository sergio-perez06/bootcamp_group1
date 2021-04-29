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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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

    // Requirement 1
    @GetMapping("/list")
    public List<PartDTO> getList(@RequestParam HashMap<String, String> params) throws Exception {
        return stockService.getParts(params);
    }

    // Requirement 2
    @GetMapping("/orders")
    public SubsidiaryOrdersByDeliveryStatusDTO getSubsidiaryOrdersByDeliveryStatus(
            @RequestParam(required = false) @NotBlank(message = "El 'dealerNumber' no puede ser vacio") @Size(min = 4, max = 4, message = "El 'dealerNumber' debe tener cuatro caracteres") String dealerNumber, // In our project is 'subsidiaryNumber'
            @RequestParam(required = false) @Pattern(regexp = "\\b[PCDF]\\b", message = "El 'deliveryStatus' no es válido (P,D,F o C") String deliveryStatus,
            @RequestParam(required = false) @Pattern(regexp = "\\b[12]\\b", message = "El 'order' no es válido (1 o 2)") String order,
            @RequestHeader(value = "Authorization", required = false) @NotBlank(message = "Debe iniciar sesión para usar este endpoint") String token) {
        Map<String,Object> claims = tokenUtils.getAllClaimsFromToken(token);

        return stockService.getSubsidiaryOrdersByDeliveryStatus(dealerNumber, claims.get("country").toString(), deliveryStatus,order);
    }

    // Requirement 4
    @PostMapping()
    public CountryDealerStockResponseDTO addPartCountryDealerStock(
            @RequestBody(required = false) @Valid CountryDealerStockDTO countryDealerStock,
            @RequestHeader(value = "Authorization", required = false) @NotBlank(message = "Debe iniciar sesión para usar este endpoint") String token) {
        Map<String,Object> claims = tokenUtils.getAllClaimsFromToken(token);

        return stockService.addStockToCountryDealer(countryDealerStock, claims.get("country").toString());
    }

    // Requirement 5
    @PostMapping("/orders")
    public BillDTO addBillCountryDealer(
            @RequestBody(required = false) @Valid BillRequestDTO billRequestDTO,
            @RequestHeader(value = "Authorization", required = false) @NotBlank(message = "Debe iniciar sesión para usar este endpoint") String token){
        Map<String,Object> claims = tokenUtils.getAllClaimsFromToken(token);

        return stockService.addBillToCountryDealer(billRequestDTO,claims.get("country").toString());
    }

    @GetMapping("/allCountryDealers")
    public List<CountryDealer> countryDealers(){
        return stockService.getAllCountryDealers();
    }
}
