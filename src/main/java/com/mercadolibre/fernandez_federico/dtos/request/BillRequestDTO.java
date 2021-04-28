package com.mercadolibre.fernandez_federico.dtos.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDetailDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BillRequestDTO {
    private String subsidiaryNumber;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate deliveryDate;
    private List<BillDetailDTO> billDetails;
}
