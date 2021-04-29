package com.mercadolibre.fernandez_federico.dtos.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CountryDealerStockDTO {
    @Size(min = 8, max = 8, message = "El 'partCode' debe tener ocho caracteres")
    private String partCode;

    @Min(value = 1, message = "El 'quantity' no puede ser menor a 1")
    private Integer quantity;
}
