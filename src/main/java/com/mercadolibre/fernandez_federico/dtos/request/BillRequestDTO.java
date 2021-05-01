package com.mercadolibre.fernandez_federico.dtos.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.mercadolibre.fernandez_federico.dtos.responses.BillDetailDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class BillRequestDTO {
    @Size(min = 4, max = 4, message = "El 'subsidiaryNumber' debe tener cuatro caracteres")
    private String subsidiaryNumber;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate deliveryDate;

    @Valid
    @NotEmpty(message = "El 'billDetails' no puede estar vacío")
    private List<PostBillDetailDTO> billDetails;
}
