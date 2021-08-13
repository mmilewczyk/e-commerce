package com.milewczyk.cartservice.controller;

import com.milewczyk.cartservice.model.Cart;
import com.milewczyk.cartservice.model.CartItem;
import com.milewczyk.cartservice.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable("userId") Long userId){
        return status(HttpStatus.OK).body(cartService.getCartByUserId(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Cart> addItemToCart(@PathVariable("userId") Long userId, @RequestBody CartItem cartItem) {
        return status(HttpStatus.CREATED).body(cartService.addItemToCart(userId, cartItem));
    }

    @DeleteMapping("/{userId}/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItemFromCart(@PathVariable Long itemId, @PathVariable Long userId) {
        cartService.removeItemFromCart(itemId, userId);
    }
}
