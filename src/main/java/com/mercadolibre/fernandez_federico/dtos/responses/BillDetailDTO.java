package com.mercadolibre.fernandez_federico.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import com.mercadolibre.fernandez_federico.util.enums.PartStatus;
import com.mercadolibre.fernandez_federico.util.enums.serializers.AccountTypeSerializer;
import com.mercadolibre.fernandez_federico.util.enums.serializers.OrderStatusSerializer;
import com.mercadolibre.fernandez_federico.util.enums.serializers.PartStatusSerializer;
import lombok.Data;

@Data
public class BillDetailDTO {
    private String partCode;

    private String description;

    private Integer quantity;

    @JsonSerialize(using = AccountTypeSerializer.class)
    private AccountType accountType;

    private String reason;

    @JsonSerialize(using = PartStatusSerializer.class)
    private PartStatus partStatus;
}
