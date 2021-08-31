package com.milewczyk.orderservice.model.models_from_other_services.user_service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private String city;
    private String postCode;
    private String country;
}
