package com.milewczyk.productcatalogservice.service;

import com.milewczyk.productcatalogservice.model.CatalogProduct;
import com.milewczyk.productcatalogservice.model.Product;
import com.milewczyk.productcatalogservice.repository.CatalogProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CatalogProductService {

    private final CatalogProductRepository catalogProductRepository;
    private final RestTemplate restTemplate;

    public Page<CatalogProduct> getAllProducts(Pageable pageable) {
        return catalogProductRepository.findAll(pageable);
    }

    public ResponseEntity<Product> getCatalogProductById(Long productId){
        return restTemplate.getForEntity("http://product-info-service/api/web/products/" + productId, Product.class);
    }

    public Page<CatalogProduct> getAllProductsByBrand(String brandName, Pageable pageable) {
        return catalogProductRepository.findProductsByBrandNameContainingIgnoreCase(brandName, pageable);
    }

    public Page<CatalogProduct> getAllProductsByName(String name, Pageable pageable) {
        return catalogProductRepository.findProductsByNameContainingIgnoreCase(name, pageable);
    }

    public CatalogProduct addNewProducts(CatalogProduct product) {
        return catalogProductRepository.save(product);
    }

    public void deleteProductById(Long productId) {
        var product = restTemplate.getForObject("https://product-info-service/api/web/products/" + productId, Product.class);
        assert product != null;
        product.setCategories(new ArrayList<>());
        restTemplate.put("https://product-info-service/api/web/products", product);
        restTemplate.delete("https://product-info-service/api/web/products/" + productId);
        catalogProductRepository.deleteById(productId);
    }
}
