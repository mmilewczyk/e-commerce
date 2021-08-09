package com.milewczyk.userservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

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
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    @NotBlank
    private GENDER gender;

    @NotBlank
    private Address address;

    @NotBlank
    private String cartId;

    public User(String username, String email, String password, Set<Role> roles, GENDER gender, Address address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.gender = gender;
        this.address = address;
    }
}
