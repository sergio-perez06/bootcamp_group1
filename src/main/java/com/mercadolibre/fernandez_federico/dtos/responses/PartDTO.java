package com.mercadolibre.fernandez_federico.dtos.responses;

import java.util.Date;

import lombok.Data;

@Data
public class PartDTO {
    private Integer idPart;
    private Integer partCode;
    private String description;
    private String maker;
    private String discountType;
    private Double normalPrice;
    private Double urgentPrice;
    private Integer netWeight;
    private Integer longDimension;
    private Integer widthDimension;
    private Integer tallDimension;
    private Date lastModification;
    private Integer quantity;
}
