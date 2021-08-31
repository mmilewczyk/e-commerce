package com.milewczyk.orderservice.model.models_from_other_services.user_service;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class User {

    private String id;
    private String username;
    private String email;
    private String password;
    private List<USER_ROLE> role;
    private GENDER gender;
    private Address address;
    private String cartId;
    private boolean isActive;

    public User(String username, String email, String password, List<USER_ROLE> role, GENDER gender, Address address, String cartId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.address = address;
        this.cartId = cartId;
    }
}
