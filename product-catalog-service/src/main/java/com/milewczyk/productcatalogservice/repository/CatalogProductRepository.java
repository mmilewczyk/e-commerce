package com.milewczyk.productcatalogservice.repository;

import com.milewczyk.productcatalogservice.model.CatalogProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CatalogProductRepository extends JpaRepository<CatalogProduct, Long> {

    @Transactional
    @EntityGraph(attributePaths = {"price", "categories"})
    Page<CatalogProduct> findProductsByBrandNameContainingIgnoreCase(String brandName, Pageable pageable);

    @Transactional
    @EntityGraph(attributePaths = {"price", "categories"})
    Page<CatalogProduct> findProductsByNameContainingIgnoreCase(String name, Pageable pageable);
}
