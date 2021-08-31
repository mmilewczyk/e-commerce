package com.milewczyk.orderservice.model.models_from_other_services.cart_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Long id;
    private String userId;
    private Set<CartItem> cartItems = new HashSet<>();

    public Cart(String userId, Set<CartItem> cartItems) {
        this.userId = userId;
        this.cartItems = cartItems;
    }
}
