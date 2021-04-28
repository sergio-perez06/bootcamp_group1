package com.mercadolibre.fernandez_federico.dtos.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import com.mercadolibre.fernandez_federico.util.enums.serializers.OrderStatusSerializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BillDTO {
    private String orderNumber;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate orderDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate deliveryDate;

    private Integer daysDelayed;

    @JsonSerialize(using = OrderStatusSerializer.class)
    private OrderStatus deliveryStatus;

    private List<BillDetailDTO> orderDetails;
}
