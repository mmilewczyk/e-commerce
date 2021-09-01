package com.milewczyk.orderservice.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {

    private String shipmentId;
    private String name;
    private BigDecimal cost;
}
