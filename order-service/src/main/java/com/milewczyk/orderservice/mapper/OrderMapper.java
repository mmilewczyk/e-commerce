package com.milewczyk.orderservice.mapper;

import com.milewczyk.orderservice.model.Order;
import com.milewczyk.orderservice.model.dto.OrderDTO;
import com.milewczyk.orderservice.model.models_from_other_services.cart_service.Cart;
import com.milewczyk.orderservice.model.models_from_other_services.user_service.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderId", source = "order.orderId")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "city", source = "user.address.city")
    @Mapping(target = "postCode", source = "user.address.postCode")
    @Mapping(target = "country", source = "user.address.country")
    @Mapping(target = "cartItems", source = "cart.cartItems")
    @Mapping(target = "nameOfShipment", source = "order.shipment.name")
    @Mapping(target = "costOfShipment", source = "order.shipment.cost")
    @Mapping(target = "totalPrice", source = "order.totalPrice")
    OrderDTO mapOrderToOrderDTO(Order order, Cart cart, User user);
}
