package com.mercadolibre.fernandez_federico.services.impl;

import com.mercadolibre.fernandez_federico.repositories.IBillDetailRepository;
import com.mercadolibre.fernandez_federico.repositories.IBillRepository;
import com.mercadolibre.fernandez_federico.services.IBillService;

public class BillService implements IBillService {
    private IBillRepository billRepository;
    private IBillDetailRepository billDetailRepository;
}
