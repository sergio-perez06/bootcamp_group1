package com.mercadolibre.fernandez_federico.dtos.responses;

import com.mercadolibre.fernandez_federico.models.BillDetail;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BillDTO {
    private Integer orderNumberCM;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private Integer daysDelayed;
    private OrderStatus deliveryStatus;

    private List<BillDetailDTO> orderDetails;

}
