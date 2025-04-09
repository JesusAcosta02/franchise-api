package com.franchise_api.infrastructure.entrypoints.mapper;

import com.franchise_api.domain.model.Branch;
import com.franchise_api.infrastructure.mongo.entity.BranchEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductEntityMapper.class)
public interface BranchEntityMapper {
    Branch toDomain(BranchEntity entity);
    BranchEntity toEntity(Branch domain);
}