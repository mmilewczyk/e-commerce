package com.milewczyk.userservice.repository;

import com.milewczyk.userservice.model.Role;
import com.milewczyk.userservice.model.USER_ROLE;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(USER_ROLE name);
}
