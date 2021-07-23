package com.milewczyk.productcatalogservice.model.dto;

import lombok.Data;

@Data
public class CatalogProductDTO {
    private Long id;
    private String name;
    private String brandName;
}
