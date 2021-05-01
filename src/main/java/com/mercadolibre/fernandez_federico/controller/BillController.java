package com.mercadolibre.fernandez_federico.controller;

import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.services.IStockWarehouseService;
import com.mercadolibre.fernandez_federico.services.impl.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    // Requerimento 3 - FALTA INTEGRATION TEST
    @GetMapping("/list")
    public BillDTO getOrderDetails(@RequestParam String orderNumberCM){
        return billService.getBillDetails(orderNumberCM);
    }
}
