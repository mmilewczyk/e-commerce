package com.milewczyk.cartservice.controller;

import com.milewczyk.cartservice.model.CartItem;
import com.milewczyk.cartservice.model.dto.CartDTO;
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

    @GetMapping("/cart")
    public ResponseEntity<CartDTO> getPrincipalCart(){
        return status(HttpStatus.OK).body(cartService.getCart());
    }

    @PostMapping("/")
    public ResponseEntity<CartDTO> addItemToCart(@RequestBody CartItem cartItem) {
        return status(HttpStatus.CREATED).body(cartService.addItemToCart(cartItem));
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItemFromCart(@PathVariable Long itemId) {
        cartService.removeItemFromCart(itemId);
    }
}
