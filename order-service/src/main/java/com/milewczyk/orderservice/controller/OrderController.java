package com.milewczyk.orderservice.controller;

import com.milewczyk.orderservice.model.Order;
import com.milewczyk.orderservice.model.dto.OrderDTO;
import com.milewczyk.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mateusz Milewczyk, github: agiklo
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getPrincipalUserOrders(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getPrincipalUserOrders(pageable));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createNewOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createNewOrder(order));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwnOrder(@PathVariable("id") Long orderId) {
        orderService.deleteOwnOrder(orderId);
    }

    @DeleteMapping("/management/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderByUserId(@PathVariable("id") Long orderId) {
        orderService.deleteOrderByUserId(orderId);
    }
}
