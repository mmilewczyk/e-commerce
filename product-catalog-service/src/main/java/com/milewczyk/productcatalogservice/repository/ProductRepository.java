package com.milewczyk.productcatalogservice.repository;

import com.milewczyk.productcatalogservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Transactional
    @EntityGraph(attributePaths = {"price", "categories"})
    Page<Product> findProductsByBrandNameContainingIgnoreCase(String brandName, Pageable pageable);

    @Transactional
    @EntityGraph(attributePaths = {"price", "categories"})
    Page<Product> findProductsByNameContainingIgnoreCase(String name, Pageable pageable);
}
