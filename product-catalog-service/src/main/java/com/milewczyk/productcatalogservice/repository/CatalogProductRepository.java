package com.milewczyk.productcatalogservice.repository;

import com.milewczyk.productcatalogservice.model.CatalogProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CatalogProductRepository extends JpaRepository<CatalogProduct, Long> {

    @Transactional
    Page<CatalogProduct> findCatalogProductsByBrandNameContainingIgnoreCase(String brandName, Pageable pageable);

    @Transactional
    Page<CatalogProduct> findCatalogProductsByNameContainingIgnoreCase(String name, Pageable pageable);
}
