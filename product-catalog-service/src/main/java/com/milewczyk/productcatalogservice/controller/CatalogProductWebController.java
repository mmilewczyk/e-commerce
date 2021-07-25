package com.milewczyk.productcatalogservice.controller;

import com.milewczyk.productcatalogservice.model.CatalogProduct;
import com.milewczyk.productcatalogservice.model.Product;
import com.milewczyk.productcatalogservice.model.dto.CatalogProductDTO;
import com.milewczyk.productcatalogservice.service.CatalogProductService;
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
public class CatalogProductWebController {

    private final CatalogProductService catalogProductService;

    @GetMapping
    public ResponseEntity<Page<CatalogProductDTO>> getAllProducts(Pageable pageable) {
        return status(HttpStatus.OK).body(catalogProductService.getAllProducts(pageable));
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> getProductById(@PathVariable Long productId){
        return catalogProductService.getCatalogProductById(productId);
    }

    @GetMapping("/search-by-brand")
    public ResponseEntity<Page<CatalogProductDTO>> getAllProductsByBrandName(@RequestParam("brand") String brandName, Pageable pageable) {
        return status(HttpStatus.OK).body(catalogProductService.getAllProductsByBrand(brandName, pageable));
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<Page<CatalogProductDTO>> getAllProductsByName(@RequestParam("name") String name, Pageable pageable) {
        return status(HttpStatus.OK).body(catalogProductService.getAllProductsByName(name, pageable));
    }
}
