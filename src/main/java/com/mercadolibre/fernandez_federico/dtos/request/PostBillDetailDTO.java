package com.mercadolibre.fernandez_federico.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PostBillDetailDTO {
    @Size(min = 8, max = 8, message = "El 'partCode' del BillDetail debe tener ocho caracteres")
    private String partCode;

    @Min(value = 1, message = "El 'quantity' del BillDetail no puede ser menor a 1")
    private Integer quantity;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private AccountType accountType;
}
