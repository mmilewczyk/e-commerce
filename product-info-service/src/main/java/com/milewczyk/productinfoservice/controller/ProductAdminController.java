package com.milewczyk.productinfoservice.controller;

import com.milewczyk.productinfoservice.model.Product;
import com.milewczyk.productinfoservice.model.dto.ProductDTO;
import com.milewczyk.productinfoservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(produces = "application/json", path = "/management/products-info")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProductAdminController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") Long productId) {
        return status(HttpStatus.OK).body(productService.getProductById(productId));
    }

    @PutMapping
    public ResponseEntity<ProductDTO> editProductContent(@RequestBody Product product){
        return status(HttpStatus.OK).body(productService.updateProduct(product));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> addNewProduct(@RequestBody Product product) {
        return status(HttpStatus.CREATED).body(productService.addNewProduct(product));
    }

    @DeleteMapping("(/{productId}")
    public void deleteProductById(@PathVariable("productId") Long productId){
        productService.deleteProductById(productId);
    }
}
