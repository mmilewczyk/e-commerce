package com.milewczyk.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String city;
    private String postCode;
    private String country;
}
