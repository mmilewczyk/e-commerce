package com.milewczyk.cartservice.model.modelsFromOtherServices;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

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
