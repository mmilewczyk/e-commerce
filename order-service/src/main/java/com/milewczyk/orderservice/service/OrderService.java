package com.milewczyk.orderservice.service;

import com.milewczyk.orderservice.mapper.OrderMapper;
import com.milewczyk.orderservice.model.Order;
import com.milewczyk.orderservice.model.dto.OrderDTO;
import com.milewczyk.orderservice.model.models_from_other_services.cart_service.Cart;
import com.milewczyk.orderservice.model.models_from_other_services.cart_service.CartItem;
import com.milewczyk.orderservice.model.models_from_other_services.user_service.USER_ROLE;
import com.milewczyk.orderservice.model.models_from_other_services.user_service.User;
import com.milewczyk.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mateusz Milewczyk, github: agiklo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final OrderMapper orderMapper;
    private final Principal principal;

    @Value("${order.configuration.allowedNumberOfHoursToDeleteAnOrder}")
    private Long allowedNumberOfHoursToDeleteAnOrder;

    public Page<OrderDTO> getPrincipalUserOrders(Pageable pageable) {
        var orders = getOrdersOfPrincipalUser(pageable);
        return new PageImpl<>(mapOrdersToDTOs(orders));
    }

    private List<OrderDTO> mapOrdersToDTOs(List<Order> orders) {
        var cart = getCartOfPrincipalUser();
        var user = getUserFromUserService();
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order: orders) {
            var orderDTO = orderMapper.mapOrderToOrderDTO(order, cart, user);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    /**
     * @return new Order object
     */
    public OrderDTO createNewOrder(Order order) {
        var cart = getCartOfPrincipalUser();
        var user = getUserFromUserService();

        order.setCartId(cart.getId());
        order.setUserId(user.getId());
        order.setDateOfOrder(LocalDateTime.now());
        order.setTotalPrice(cart.getCartItems().stream()
                        .map(CartItem::getTotalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        log.info("User " + order.getUserId() + " created a new order " + order.getOrderId());
        var newOrder = orderRepository.save(order);
        return orderMapper.mapOrderToOrderDTO(newOrder, cart, user);
    }

    /**
     * A method that allows the deletion of the order of the user, provided that allowed number of hours have not passed since the order was placed!
     * @exception ResponseStatusException an exception will be thrown if allowed number of hours have passed since the order was placed
     */
    public void deleteOwnOrder(Long orderId) {
        var user = getUserFromUserService();
        var order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("Order " + orderId + " does not exist!"));

        // the condition checks if the authenticated user is the owner of the order.
        if (order.getUserId().equals(user.getId())) {
            if (order.getDateOfOrder().isBefore(order.getDateOfOrder().plusHours(allowedNumberOfHoursToDeleteAnOrder))) {
                log.info("User " + order.getUserId() + " deleted order " + order.getOrderId());
                orderRepository.deleteById(order.getOrderId());
            } else {
                log.error("User " + order.getUserId() + " failed to delete order: " + order.getOrderId());
                throw new ResponseStatusException(HttpStatus.NOT_MODIFIED,
                        "The order could only be cancelled until:" + order.getDateOfOrder().plusHours(allowedNumberOfHoursToDeleteAnOrder));
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not the owner of order " + orderId);
        }
    }

    /**
     * A method that allows the moderator to delete a user's order by providing the order ID
     * @param orderId the ID of the order we want to delete
     * @exception ResponseStatusException an exception will be thrown if the principal does not have a moderator role
     */
    public void deleteOrderByUserId(Long orderId) {
        var user = getUserFromUserService();
        if(user.getRole().contains(USER_ROLE.ROLE_MODERATOR)) {
            log.info("Moderator " + user.getId() + " deleted order " + orderId);
            orderRepository.deleteById(orderId);
        } else {
            log.info("Unauthorized user " + user.getId() + " failed to delete order: " + orderId);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "You are not authenticated as: " + USER_ROLE.ROLE_MODERATOR.name());
        }
    }

    /**
     *
     * @return Object of the Principal user
     */
    private User getUserFromUserService() {
        return restTemplate.getForObject("http://user-service/" +
                principal.getName(), User.class);
    }

    private List<Order> getOrdersOfPrincipalUser(Pageable pageable) {
        var user = getUserFromUserService();
        return orderRepository.findByUserId(user.getId(), pageable)
                .orElseThrow(() -> new IllegalArgumentException("You are not authenticated, login!"));
    }

    private Cart getCartOfPrincipalUser() {
        return restTemplate.getForObject("http://cart-service/cart", Cart.class);
    }
}
