package com.franchise_api.infrastructure.entrypoints.dto.mappers;


import com.franchise_api.domain.model.Franchise;
import com.franchise_api.infrastructure.entrypoints.dto.FranchiseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseDtoMapper {
    FranchiseDTO toDto(Franchise franchise);

    Franchise toDomain(FranchiseDTO dto);
}
