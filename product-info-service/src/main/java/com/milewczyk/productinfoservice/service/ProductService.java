package com.milewczyk.productinfoservice.service;

import com.milewczyk.productinfoservice.model.CatalogProduct;
import com.milewczyk.productinfoservice.model.Product;
import com.milewczyk.productinfoservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public Product getProductById(Long productId) {
        return productRepository.findProductById(productId);
    }

    public Product addNewProduct(Product product){
        var catalogProduct = new CatalogProduct(product.getId(), product.getName(), product.getBrandName());
        restTemplate.postForObject("http://product-catalog-service/api/admin/products", catalogProduct, CatalogProduct.class);
        return productRepository.save(product);
    }

    public Product updateProduct(Product product){
        var editedProduct = productRepository.findById(product.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product cannot be found"));
        editedProduct.setName(product.getName());
        editedProduct.setBrandName(product.getBrandName());
        editedProduct.setSize(product.getSize());
        editedProduct.setAvaliable(product.getAvaliable());
        editedProduct.setDescription(product.getDescription());
        editedProduct.setPrice(product.getPrice());
        return editedProduct;
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }
}
