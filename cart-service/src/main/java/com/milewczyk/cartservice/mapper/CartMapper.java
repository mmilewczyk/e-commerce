package com.milewczyk.cartservice.mapper;

import com.milewczyk.cartservice.model.Cart;
import com.milewczyk.cartservice.model.dto.CartDTO;
import com.milewczyk.cartservice.model.models_from_other_services.userservice.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "id", source = "cart.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "cartItems", source = "cart.cartItems")
    CartDTO mapCartToCartDTO(Cart cart, User user);
}
