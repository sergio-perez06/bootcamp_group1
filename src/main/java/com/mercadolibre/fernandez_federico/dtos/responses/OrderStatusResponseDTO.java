package com.mercadolibre.fernandez_federico.dtos.responses;

import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderStatusResponseDTO {

    private Long orderNumberCE;

    private LocalDate orderDate;

    private OrderStatus orderStatus;

    private List<BillDetailDTO> orderDetails;

}
