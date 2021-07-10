package com.milewczyk.productinfoservice.model.dto;

import com.milewczyk.productinfoservice.model.Category;
import com.milewczyk.productinfoservice.model.Price;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brandName;
    private Double size;
    private Boolean avaliable;
    private String description;
    private Integer amount;
    private Price price;
    private List<Category> categories;
}
