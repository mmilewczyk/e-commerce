package com.milewczyk.productinfoservice.service;

import com.milewczyk.productinfoservice.model.CatalogProduct;
import com.milewczyk.productinfoservice.model.Product;
import com.milewczyk.productinfoservice.model.dto.ProductDTO;
import com.milewczyk.productinfoservice.model.mapper.ProductMapper;
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
    private final ProductMapper productMapper;

    private static final String PRODUCT_CATALOG_SERVICE_URL = "http://product-catalog-service/api/admin/products";

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        var product = productRepository.findProductById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product cannot be found, the specified id does not exist"));
        return productMapper.mapProductToDTO(product);
    }

    @Transactional
    public ProductDTO addNewProduct(Product product){
        postProductToProductCatalogService(PRODUCT_CATALOG_SERVICE_URL, product);
        var newProduct = productRepository.save(product);
        return productMapper.mapProductToDTO(newProduct);
    }

    @Transactional
    public void postProductToProductCatalogService(String url, Product product){
        var catalogProduct = new CatalogProduct(product.getId(), product.getName(), product.getBrandName());
        restTemplate.postForObject(url, catalogProduct, CatalogProduct.class);
    }

    @Transactional
    public ProductDTO updateProduct(Product product){
        var editedProduct = productRepository.findById(product.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product cannot be found"));
        editedProduct.setName(product.getName());
        editedProduct.setBrandName(product.getBrandName());
        editedProduct.setSize(product.getSize());
        editedProduct.setAvaliable(product.getAvaliable());
        editedProduct.setDescription(product.getDescription());
        editedProduct.setPrice(product.getPrice());
        return productMapper.mapProductToDTO(editedProduct);
    }

    @Transactional
    public void deleteProductById(Long productId) {
        try {
            productRepository.deleteById(productId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Wrong id, product " + productId + " does not exist!", e);
        }
    }
}
