package com.milewczyk.productinfoservice.service;

import com.milewczyk.productinfoservice.model.Category;
import com.milewczyk.productinfoservice.model.Price;
import com.milewczyk.productinfoservice.model.Product;
import com.milewczyk.productinfoservice.model.dto.ProductDTO;
import com.milewczyk.productinfoservice.model.mapper.ProductMapper;
import com.milewczyk.productinfoservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ProductService.class})
@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceTest {

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    @Test
    void shouldGetProductById() {
        // given
        Product product = new Product(123L, "Model", "Nike", 44.5,
                true, "description", 1, Price.ZERO, new ArrayList<Category>());
        ProductDTO productDTO = new ProductDTO();

        // when
        when(this.productRepository.findProductById(any())).thenReturn(Optional.of(product));
        when(this.productMapper.mapProductToDTO(any())).thenReturn(productDTO);

        // then
        assertSame(productDTO, this.productService.getProductById(123L));
        verify(this.productMapper).mapProductToDTO(any());
        verify(this.productRepository).findProductById(any());
    }

    @Test
    void shouldNotGetProductByIdAndThrowsException() {
        // when
        when(this.productRepository.findProductById(any())).thenReturn(Optional.empty());
        when(this.productMapper.mapProductToDTO(any())).thenReturn(new ProductDTO());

        // then
        assertThrows(ResponseStatusException.class, () -> this.productService.getProductById(123L));
        verify(this.productRepository).findProductById(any());
    }

    @Test
    void shouldAddNewProduct() {
        Product product = new Product(123L, "Model", "Nike", 44.5,
                true, "description", 1, Price.ZERO, new ArrayList<Category>());
        ProductDTO productDTO = new ProductDTO();

        when(this.productRepository.save(any())).thenReturn(product);
        when(this.productMapper.mapProductToDTO(any())).thenReturn(productDTO);

        assertSame(productDTO, this.productService.addNewProduct(new Product()));
        verify(this.productRepository).save(any());
        verify(this.productMapper).mapProductToDTO(any());
    }

    @Test
    void shouldDeleteProductById() {
        doNothing().when(this.productRepository).deleteById(anyLong());
        this.productService.deleteProductById(123L);
        verify(this.productRepository).deleteById(anyLong());
    }

    @Test
    void shouldNotDeleteProductByIdAndThrowsException() {
        doThrow(new IllegalArgumentException()).when(this.productRepository).deleteById(anyLong());
        assertThrows(IllegalArgumentException.class, () -> this.productService.deleteProductById(123L));
        verify(this.productRepository).deleteById(anyLong());
    }

}
