package com.milewczyk.userservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "roles")
public class Role {

    @Id
    private String id;
    private USER_ROLE name;

    public Role(USER_ROLE name) {
        this.name = name;
    }
}
