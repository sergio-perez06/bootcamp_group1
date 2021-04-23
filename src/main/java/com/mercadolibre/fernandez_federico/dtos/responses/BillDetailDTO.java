package com.mercadolibre.fernandez_federico.dtos.responses;

import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import com.mercadolibre.fernandez_federico.util.enums.PartStatus;
import lombok.Data;

@Data
public class BillDetailDTO {

    private Integer partCode;

    private String description;

    private Integer quantity;

    private AccountType accountType;

    private String reason;

    private PartStatus partStatus;



}
