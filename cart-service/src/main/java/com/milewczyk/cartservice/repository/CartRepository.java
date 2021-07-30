package com.milewczyk.cartservice.repository;

import com.milewczyk.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Long, Cart> {

    Optional<Cart> findByuserId(Long userId);
}
