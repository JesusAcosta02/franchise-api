package com.franchise_api.infrastructure.mapper;



import com.franchise_api.domain.model.Product;
import com.franchise_api.infrastructure.mongo.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {
    Product toDomain(ProductEntity entity);
    ProductEntity toEntity(Product domain);
}