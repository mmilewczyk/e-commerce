package com.milewczyk.productcatalogservice.service;

import com.milewczyk.productcatalogservice.model.CatalogProduct;
import com.milewczyk.productcatalogservice.model.Price;
import com.milewczyk.productcatalogservice.model.Product;
import com.milewczyk.productcatalogservice.model.dto.CatalogProductDTO;
import com.milewczyk.productcatalogservice.model.mapper.CatalogProductMapper;
import com.milewczyk.productcatalogservice.repository.CatalogProductRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.status;

@Slf4j
@Service
@AllArgsConstructor
public class CatalogProductService {

    private final CatalogProductRepository catalogProductRepository;
    private final RestTemplate restTemplate;
    private final CatalogProductMapper catalogProductMapper;

    public Page<CatalogProductDTO> getAllProducts(Pageable pageable) {
        List<CatalogProductDTO> catalogProducts = catalogProductRepository.findAll(pageable).stream()
                .map(catalogProductMapper::mapCatalogProductToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(catalogProducts);
    }

    @HystrixCommand(fallbackMethod = "getFallbackCatalogProductById")
    public ResponseEntity<Product> getCatalogProductById(Long productId){
        log.info("Fetching Product entity from PRODUCT INFO SERVICE");
        return restTemplate.getForEntity("http://product-info-service/api/web/products/" + productId, Product.class);
    }

    public ResponseEntity<Product> getFallbackCatalogProductById(Long productId){
        log.info("Returning fallback Product object");
        Optional<CatalogProduct> catalogProduct = catalogProductRepository.findById(productId);
        return catalogProduct.map(product -> status(HttpStatus.OK)
                .body(new Product(productId, product.getName(), product.getBrandName(), 0.0, false, "Fallback", 0, Price.ZERO, new ArrayList<>())))
                .orElseGet(() -> status(HttpStatus.NOT_FOUND).body(new Product()));
    }

    public Page<CatalogProductDTO> getAllProductsByBrand(String brandName, Pageable pageable) {
        List<CatalogProductDTO> catalogProducts = catalogProductRepository.findCatalogProductsByBrandNameContainingIgnoreCase(brandName, pageable).stream()
                .map(catalogProductMapper::mapCatalogProductToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(catalogProducts);
    }

    public Page<CatalogProductDTO> getAllProductsByName(String name, Pageable pageable) {
        List<CatalogProductDTO> catalogProducts = catalogProductRepository.findCatalogProductsByNameContainingIgnoreCase(name, pageable).stream()
                .map(catalogProductMapper::mapCatalogProductToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(catalogProducts);
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
