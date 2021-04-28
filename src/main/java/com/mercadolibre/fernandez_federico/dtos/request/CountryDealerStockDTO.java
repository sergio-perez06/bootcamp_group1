package com.mercadolibre.fernandez_federico.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDealerStockDTO {
    private String partCode;
    private Integer quantity;
}
