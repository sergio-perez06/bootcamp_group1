package com.mercadolibre.fernandez_federico.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class SubsidiaryOrdersByDeliveryStatusDTO {
    private String subsidiaryNumber;
    private List<BillDTO> orders;
}
