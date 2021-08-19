package com.milewczyk.userservice.model;

import com.milewczyk.userservice.enums.GENDER;
import com.milewczyk.userservice.enums.USER_ROLE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Indexed(unique = true)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Enumerated(EnumType.STRING)
    @DBRef
    private List<USER_ROLE> role;

    @NotBlank
    private GENDER gender;

    @NotBlank
    private Address address;

    @NotBlank
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
