package com.milewczyk.productinfoservice.model.mapper;

import com.milewczyk.productinfoservice.model.Product;
import com.milewczyk.productinfoservice.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "brandName", target = "brandName")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "avaliable", target = "avaliable")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "categories", target = "categories")
    ProductDTO mapProductToDTO(Product product);
}
