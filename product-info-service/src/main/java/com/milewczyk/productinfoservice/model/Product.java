package com.milewczyk.productinfoservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brandName;
    private Double size;
    private Boolean avaliable;

    @Lob
    private String description;

    private Integer amount;

    @Embedded
    private Price price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "products_categories", joinColumns = {
            @JoinColumn(name = "product_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "category_id", referencedColumnName = "id")})
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

