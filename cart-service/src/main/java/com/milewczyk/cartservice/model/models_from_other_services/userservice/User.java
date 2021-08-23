package com.milewczyk.cartservice.model.models_from_other_services.userservice;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
