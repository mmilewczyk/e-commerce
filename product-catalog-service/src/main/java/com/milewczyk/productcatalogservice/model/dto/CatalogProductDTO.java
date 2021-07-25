package com.milewczyk.productcatalogservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogProductDTO {
    private Long id;
    private String name;
    private String brandName;
}
