package com.milewczyk.cartservice.model.dto;

import com.milewczyk.cartservice.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long id;
    private String userId;
    private String username;
    private String email;
    private Set<CartItem> cartItems;
}
