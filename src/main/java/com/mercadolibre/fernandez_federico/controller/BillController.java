package com.mercadolibre.fernandez_federico.controller;

import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;
import com.mercadolibre.fernandez_federico.services.impl.BillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class BillController {
    private BillService billService;

    @GetMapping("/orders")
    public List<BillDTO> getOrderDetails(@RequestParam String oNum){
        return billService.getBillDetails(oNum);
    }
}
