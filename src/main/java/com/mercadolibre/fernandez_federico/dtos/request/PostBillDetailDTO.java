package com.mercadolibre.fernandez_federico.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import lombok.Data;

@Data
public class PostBillDetailDTO {
    private String partCode;
    private Integer quantity;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private AccountType accountType;
}
