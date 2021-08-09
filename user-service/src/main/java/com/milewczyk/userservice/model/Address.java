package com.milewczyk.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private String city;
    private String postCode;
    private String country;
}
