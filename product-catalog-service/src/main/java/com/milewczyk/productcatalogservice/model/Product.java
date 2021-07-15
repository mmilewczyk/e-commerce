package com.milewczyk.productcatalogservice.model;

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

    public Product(String brandName, String name, Price price, Double size, Integer amount) {
        this.brandName = brandName;
        this.name = name;
        this.price = price;
        this.size = size;
        this.amount = amount;
        this.avaliable = isAvaliable();
    }

    public Boolean isAvaliable() {
        return this.amount > 0;
    }
}
