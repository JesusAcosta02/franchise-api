package com.franchise_api.infrastructure.mapper;


import com.franchise_api.domain.model.Franchise;
import com.franchise_api.infrastructure.mongo.entity.FranchiseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BranchEntityMapper.class)
public interface FranchiseEntityMapper {
    Franchise toDomain(FranchiseEntity entity);
    FranchiseEntity toEntity(Franchise domain);
}