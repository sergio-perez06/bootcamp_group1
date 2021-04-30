package com.mercadolibre.fernandez_federico.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mercadolibre.fernandez_federico.util.CodeNumberSerializator;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
public class BillDTO {
    @NonNull
    @JsonSerialize(using = CodeNumberSerializator.class)
    private String orderNumber;

    @NonNull
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate orderDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate deliveryDate;

    private Integer daysDelayed;

    @NonNull
    private OrderStatus deliveryStatus;

    @NonNull
    private List<BillDetailDTO> orderDetails;

    public BillDTO() {
        
    }
}
