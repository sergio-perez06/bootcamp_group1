package com.mercadolibre.fernandez_federico.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import com.mercadolibre.fernandez_federico.util.enums.PartStatus;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BillDetailDTO {
    @NonNull
    private String partCode;
    @NonNull
    private String description;
    @NonNull
    private Integer quantity;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @NonNull
    private AccountType accountType;
    @NonNull
    private String reason;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @NonNull
    private PartStatus partStatus;

    public BillDetailDTO() {

    }
}
