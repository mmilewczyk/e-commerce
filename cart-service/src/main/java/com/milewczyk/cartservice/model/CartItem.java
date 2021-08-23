package com.milewczyk.cartservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "CART_ITEMS")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productName;
    private String brandName;
    private Long amountOfProduct;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPrice;

    @ManyToOne
    @JsonIgnore
    private Cart cart;
}
