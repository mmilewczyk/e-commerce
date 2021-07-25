package com.milewczyk.productcatalogservice.model.mapper;

import com.milewczyk.productcatalogservice.model.CatalogProduct;
import com.milewczyk.productcatalogservice.model.dto.CatalogProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogProductMapper {

    CatalogProductDTO mapCatalogProductToDTO(CatalogProduct catalogProduct);
}
