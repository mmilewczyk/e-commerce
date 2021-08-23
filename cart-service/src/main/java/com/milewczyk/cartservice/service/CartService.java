package com.milewczyk.cartservice.service;

import com.milewczyk.cartservice.model.Cart;
import com.milewczyk.cartservice.model.CartItem;
import com.milewczyk.cartservice.model.models_from_other_services.productinfoservice.Product;
import com.milewczyk.cartservice.model.models_from_other_services.userservice.User;
import com.milewczyk.cartservice.repository.CartItemRepository;
import com.milewczyk.cartservice.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final RestTemplate restTemplate;
    private final Principal principal;

    @Transactional
    public Cart getCart() {
        var cart = getCartOfPrincepal();
        mapCartItemsToGetInfo(cart);
        return cart;
    }

    private void mapCartItemsToGetInfo(Cart cart) {
        for (CartItem item: cart.getCartItems()) {
            var product = restTemplate.getForEntity(
                    "http://product-info-service/api/web/products/" + item.getProductId(),
                    Product.class).getBody();

            assert product != null;

            item.setProductName(product.getName());
            item.setBrandName(product.getBrandName());
            item.setPricePerUnit(product.getPrice().getValue());
            item.setTotalPrice(item.getPricePerUnit().multiply(BigDecimal.valueOf(item.getAmountOfProduct())));
        }
    }

    public Cart addItemToCart(CartItem cartItem) {
        var cart = getCartOfPrincepal();

        if (cart.getCartItems().contains(cartItem)) {
            cartItem.setAmountOfProduct(cartItem.getAmountOfProduct() + 1);
        } else {
            cart.getCartItems().add(cartItem);
        }
        return cart;
    }

    public void removeItemFromCart(Long itemId){
        var cart = getCartOfPrincepal();
        CartItem itemToRemove = cartItemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("Product does not exist!"));

        if (cart.getCartItems().contains(itemToRemove)){
            itemToRemove.setAmountOfProduct(itemToRemove.getAmountOfProduct() - 1);
        } else {
            cart.getCartItems().remove(itemToRemove);
        }
    }

    private Cart getCartOfPrincepal() {
        var user = getUserFromUserService();
        assert user != null;
        return cartRepository.findByUserId(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("User does not exist!"));
    }

    private User getUserFromUserService() {
        return restTemplate.getForObject("http://user-service/" + principal.getName(), User.class);
    }
}
