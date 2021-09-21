package com.milewczyk.userservice.model;

import com.milewczyk.userservice.enums.Gender;
import com.milewczyk.userservice.enums.User_role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    private User_role role;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    @Embedded
    private Address address;

    @NotBlank
    private Long cartId;

    private boolean isActive;

    public User(String username, String email, String password, User_role role, Gender gender, Address address, Long cartId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.address = address;
        this.cartId = cartId;
    }
}
