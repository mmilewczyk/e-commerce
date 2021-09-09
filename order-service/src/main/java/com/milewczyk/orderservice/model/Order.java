package com.milewczyk.orderservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long cartId;
    private String userId;

    @OneToOne
    private Shipment shipment;

    private LocalDateTime dateOfOrder;
    private BigDecimal totalPrice;

    public Order(Long cartId, String userId, Shipment shipment, LocalDateTime dateOfOrder, BigDecimal totalPrice) {
        this.cartId = cartId;
        this.userId = userId;
        this.shipment = shipment;
        this.dateOfOrder = dateOfOrder;
        this.totalPrice = totalPrice;
    }
}
