package com.mercadolibre.fernandez_federico.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mercadolibre.fernandez_federico.models.BillDetail;
import com.mercadolibre.fernandez_federico.util.CodeNumberSerializator;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BillDTO {
    @JsonSerialize(using = CodeNumberSerializator.class)
    private String orderNumber;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate orderDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate deliveryDate;

    private Integer daysDelayed;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private OrderStatus deliveryStatus;
    private List<BillDetailDTO> orderDetails;
}
