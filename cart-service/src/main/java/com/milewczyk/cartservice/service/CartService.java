package com.milewczyk.cartservice.service;

import com.milewczyk.cartservice.mapper.CartMapper;
import com.milewczyk.cartservice.model.CartItem;
import com.milewczyk.cartservice.model.dto.CartDTO;
import com.milewczyk.cartservice.model.models_from_other_services.productinfoservice.Product;
import com.milewczyk.cartservice.model.models_from_other_services.userservice.GENDER;
import com.milewczyk.cartservice.model.models_from_other_services.userservice.User;
import com.milewczyk.cartservice.repository.CartItemRepository;
import com.milewczyk.cartservice.repository.CartRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;

@Slf4j
@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final RestTemplate restTemplate;
    private final Principal principal;
    private final CartMapper cartMapper;

    @Transactional
    public CartDTO getCart() {
        var cart = getCartOfPrincepal();
        mapCartItemsToGetInfo(cart);
        return cart;
    }

    private void mapCartItemsToGetInfo(CartDTO cart) {
        for (CartItem item : cart.getCartItems()) {
            var product = restTemplate.getForEntity(
                    "http://product-info-service/products-info" + item.getProductId(),
                    Product.class).getBody();

            assert product != null;

            item.setProductName(product.getName());
            item.setBrandName(product.getBrandName());
            item.setPricePerUnit(product.getPrice().getValue());
            item.setTotalPrice(item.getPricePerUnit().multiply(BigDecimal.valueOf(item.getAmountOfProduct())));
        }
    }

    public CartDTO addItemToCart(CartItem cartItem) {
        var cart = getCartOfPrincepal();

        if (cart.getCartItems().contains(cartItem)) {
            cartItem.setAmountOfProduct(cartItem.getAmountOfProduct() + 1);
        } else {
            cart.getCartItems().add(cartItem);
        }
        log.info("User " + cart.getUserId() + " added a new item " + cartItem.getProductId() + " to cart");
        return cart;
    }

    public void removeItemFromCart(Long itemId) {
        var cart = getCartOfPrincepal();
        CartItem itemToRemove = cartItemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("Product does not exist!"));

        if (cart.getCartItems().contains(itemToRemove)) {
            itemToRemove.setAmountOfProduct(itemToRemove.getAmountOfProduct() - 1);
        } else {
            cart.getCartItems().remove(itemToRemove);
        }
        log.info("User " + cart.getUserId() + " removed item " + itemId + " from cart");
    }

    private CartDTO getCartOfPrincepal() {
        var user = getUserFromUserService();
        assert user != null;
        var cart = cartRepository.findByUserId(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("User does not exist!"));

        return cartMapper.mapCartToCartDTO(cart, user);
    }

    @HystrixCommand(fallbackMethod = "getFallbackUserFromUserService")
    private User getUserFromUserService() {
        return restTemplate.getForObject("http://user-service/" + principal.getName(), User.class);
    }

    public User getFallbackUserFromUserService() {
        return new User("fallbackId", "fallbackUsername", "fallbackEmail", "fallbackPassword",
                new ArrayList<>(), GENDER.OTHER, null, 0L, false);
    }
}
