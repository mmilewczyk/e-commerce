package com.milewczyk.productcatalogservice.controller;

import com.milewczyk.productcatalogservice.model.Product;
import com.milewczyk.productcatalogservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(produces = "application/json", path = "api/web/products")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProductWebController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        return status(HttpStatus.OK).body(productService.getAllProducts(pageable));
    }

    @GetMapping("/search-by-brand")
    public ResponseEntity<Page<Product>> getAllProductsByBrandName(@RequestParam("brand") String brandName, Pageable pageable) {
        return status(HttpStatus.OK).body(productService.getAllProductsByBrand(brandName, pageable));
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<Page<Product>> getAllProductsByName(@RequestParam("name") String name, Pageable pageable) {
        return status(HttpStatus.OK).body(productService.getAllProductsByName(name, pageable));
    }
}
