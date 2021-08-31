package com.milewczyk.orderservice.model.models_from_other_services.cart_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private Long id;
    private Long productId;
    private String productName;
    private String brandName;
    private Long amountOfProduct;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPrice;
    private Cart cart;

    public CartItem(Long productId, String productName, String brandName, Long amountOfProduct, BigDecimal pricePerUnit, BigDecimal totalPrice, Cart cart) {
        this.productId = productId;
        this.productName = productName;
        this.brandName = brandName;
        this.amountOfProduct = amountOfProduct;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = totalPrice;
        this.cart = cart;
    }
}
