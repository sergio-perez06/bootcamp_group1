package com.mercadolibre.fernandez_federico.controller;

import java.util.HashMap;
import java.util.List;

import com.mercadolibre.fernandez_federico.dtos.responses.PartDTO;
import com.mercadolibre.fernandez_federico.services.IStockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parts")
public class PartController {

    @Autowired
    private IStockService stockService;

    @GetMapping("/list")
    public List<PartDTO> getList(
        HashMap<String, String> params    
    ) {
        return stockService.getParts(params);
    }
}