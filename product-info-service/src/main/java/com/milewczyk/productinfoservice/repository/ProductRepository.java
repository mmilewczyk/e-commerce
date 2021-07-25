package com.milewczyk.productinfoservice.repository;

import com.milewczyk.productinfoservice.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"price", "categories"})
    Optional<Product> findProductById(Long id);
}
