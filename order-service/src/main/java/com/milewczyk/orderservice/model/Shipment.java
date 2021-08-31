package com.milewczyk.orderservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
public class Shipment {

    @Id
    private String shipmentId;
    private String name;
    private BigDecimal cost;

    public Shipment(String name, BigDecimal cost) {
        this.name = name;
        this.cost = cost;
    }
}
