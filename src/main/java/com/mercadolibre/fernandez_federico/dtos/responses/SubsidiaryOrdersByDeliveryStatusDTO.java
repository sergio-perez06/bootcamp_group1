package com.mercadolibre.fernandez_federico.dtos.responses;

import lombok.Data;

@Data
public class SubsidiaryOrdersByDeliveryStatusDTO {

    private Integer subsidiaryNumber;

    private BillDTO order;

}
