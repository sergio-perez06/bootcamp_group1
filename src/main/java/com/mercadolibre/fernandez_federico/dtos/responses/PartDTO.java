package com.mercadolibre.fernandez_federico.dtos.responses;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartDTO {
    private String partCode;
    private String description;
    private String maker;
    private Integer quantity;
    private String discountType;
    private Double normalPrice;
    private Double urgentPrice;
    private Integer netWeight;
    private Integer longDimension;
    private Integer widthDimension;
    private Integer tallDimension;
    private String lastModification;
}
