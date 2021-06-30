package com.milewczyk.productcatalogservice.service;

import com.milewczyk.productcatalogservice.model.Product;
import com.milewczyk.productcatalogservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> getAllProductsByBrand(String brandName, Pageable pageable) {
        return productRepository.findProductsByBrandNameContainingIgnoreCase(brandName, pageable);
    }

    public Page<Product> getAllProductsByName(String name, Pageable pageable) {
        return productRepository.findProductsByNameContainingIgnoreCase(name, pageable);
    }

    public Product addNewProducts(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long productId) {
        var productToDelete = productRepository.findById(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product cannot be found, the specified id does not exist"));
        productToDelete.setCategories(new ArrayList<>());
        productRepository.deleteById(productId);
    }
}
