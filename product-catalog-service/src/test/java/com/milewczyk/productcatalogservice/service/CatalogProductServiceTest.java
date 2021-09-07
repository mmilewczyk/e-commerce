package com.milewczyk.productcatalogservice.service;

import com.milewczyk.productcatalogservice.model.CatalogProduct;
import com.milewczyk.productcatalogservice.model.dto.CatalogProductDTO;
import com.milewczyk.productcatalogservice.model.mapper.CatalogProductMapper;
import com.milewczyk.productcatalogservice.repository.CatalogProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CatalogProductService.class})
@SpringBootTest
@AutoConfigureMockMvc
class CatalogProductServiceTest {

    @MockBean
    private CatalogProductMapper catalogProductMapper;

    @MockBean
    private CatalogProductRepository catalogProductRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private CatalogProductService catalogProductService;

    @Test
    void shouldGetAllProducts() {
        CatalogProduct catalogProduct = new CatalogProduct(123L, "AirMax", "Nike");
        ArrayList<CatalogProduct> catalogProducts = new ArrayList<>();
        catalogProducts.add(catalogProduct);
        PageImpl<CatalogProduct> pageImpl = new PageImpl<>(catalogProducts);

        when(this.catalogProductRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        when(this.catalogProductMapper.mapCatalogProductToDTO(any())).thenReturn(new CatalogProductDTO());

        assertEquals(1, this.catalogProductService.getAllProducts(null).getSize());
        verify(this.catalogProductMapper).mapCatalogProductToDTO(any());
        verify(this.catalogProductRepository).findAll((Pageable) any());
    }

    @Test
    void shouldGetEmptyListOfCatalogProducts() {
        when(this.catalogProductRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(this.catalogProductService.getAllProducts(null).isEmpty());
        verify(this.catalogProductRepository).findAll((Pageable) any());
    }

    @Test
    void shouldGetAllProductsByBrand() {
        CatalogProduct catalogProduct = new CatalogProduct(123L, "AirMax", "Nike");

        ArrayList<CatalogProduct> catalogProducts = new ArrayList<>();
        catalogProducts.add(catalogProduct);

        when(this.catalogProductRepository.findCatalogProductsByBrandNameContainingIgnoreCase(any(), (Pageable) any())).thenReturn(new PageImpl<>(catalogProducts));
        when(this.catalogProductMapper.mapCatalogProductToDTO(any())).thenReturn(new CatalogProductDTO());

        assertThat(this.catalogProductService.getAllProductsByBrand(any(), any())).hasSize(1);
        verify(this.catalogProductMapper).mapCatalogProductToDTO(any());
        verify(this.catalogProductRepository).findCatalogProductsByBrandNameContainingIgnoreCase(any(), (Pageable) any());
    }

    @Test
    void shouldGetEmptyListOfProductsByBrand() {
        when(this.catalogProductRepository.findCatalogProductsByBrandNameContainingIgnoreCase(any(), (Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(this.catalogProductMapper.mapCatalogProductToDTO(any())).thenReturn(new CatalogProductDTO());

        assertThat(this.catalogProductService.getAllProductsByBrand(any(), any())).isEmpty();
        verify(this.catalogProductRepository).findCatalogProductsByBrandNameContainingIgnoreCase(any(), (Pageable) any());
    }

    @Test
    void shouldGetAllProductsByName() {
        CatalogProduct catalogProduct = new CatalogProduct(123L, "AirMax", "Nike");

        ArrayList<CatalogProduct> catalogProducts = new ArrayList<>();
        catalogProducts.add(catalogProduct);

        when(this.catalogProductRepository.findCatalogProductsByNameContainingIgnoreCase(any(), (Pageable) any())).thenReturn(new PageImpl<>(catalogProducts));
        when(this.catalogProductMapper.mapCatalogProductToDTO(any())).thenReturn(new CatalogProductDTO());

        assertThat(this.catalogProductService.getAllProductsByName(anyString(), any())).hasSize(1);
        verify(this.catalogProductMapper).mapCatalogProductToDTO(any());
        verify(this.catalogProductRepository).findCatalogProductsByNameContainingIgnoreCase(any(), (Pageable) any());
    }

    @Test
    void shouldGetEmptyListOfProductsByName() {
        when(this.catalogProductRepository.findCatalogProductsByNameContainingIgnoreCase(any(), (Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(this.catalogProductMapper.mapCatalogProductToDTO(any())).thenReturn(new CatalogProductDTO());

        assertThat(this.catalogProductService.getAllProductsByName(any(), any())).isEmpty();
        verify(this.catalogProductRepository).findCatalogProductsByNameContainingIgnoreCase(any(), (Pageable) any());
    }
}
