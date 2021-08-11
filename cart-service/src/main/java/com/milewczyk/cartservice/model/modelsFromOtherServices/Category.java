package com.milewczyk.cartservice.model.modelsFromOtherServices;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long id;
    private String name;
    private List<Product> products;
}
