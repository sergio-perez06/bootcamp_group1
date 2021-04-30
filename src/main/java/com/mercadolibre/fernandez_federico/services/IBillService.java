package com.mercadolibre.fernandez_federico.services;

import com.mercadolibre.fernandez_federico.dtos.responses.BillDTO;

public interface IBillService {
    public BillDTO getBillDetails(String orderNumberCM);
}
