package com.franchise_api.entrypoints.dto.mappers;

import com.franchise_api.domain.model.Product;
import com.franchise_api.entrypoints.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {
    ProductDTO toDto(Product product);
    Product toDomain(ProductDTO dto);
}