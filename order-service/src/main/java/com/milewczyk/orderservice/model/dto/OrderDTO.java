package com.milewczyk.orderservice.model.dto;

import com.milewczyk.orderservice.model.models_from_other_services.cart_service.CartItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long orderId;
    private String username;
    private String email;
    private String city;
    private String postCode;
    private String country;
    private Set<CartItem> cartItems;
    private String nameOfShipment;
    private BigDecimal costOfShipment;
    private BigDecimal totalPrice;
}
