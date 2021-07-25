package com.milewczyk.productcatalogservice.controller;

import com.milewczyk.productcatalogservice.model.CatalogProduct;
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
@RequestMapping(produces = "application/json", path = "api/admin/products")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CatalogProductAdminController {

    private final CatalogProductService catalogProductService;

    @GetMapping
    public ResponseEntity<Page<CatalogProductDTO>> getAllProducts(Pageable pageable) {
        return status(HttpStatus.OK).body(catalogProductService.getAllProducts(pageable));
    }

    @PostMapping
    public ResponseEntity<CatalogProduct> addNewProduct(@RequestBody CatalogProduct catalogProduct) {
        return status(HttpStatus.CREATED).body(catalogProductService.addNewProducts(catalogProduct));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        try {
            catalogProductService.deleteProductById(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
