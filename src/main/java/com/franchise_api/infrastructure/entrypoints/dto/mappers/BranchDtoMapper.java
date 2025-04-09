package com.franchise_api.infrastructure.entrypoints.dto.mappers;

import com.franchise_api.domain.model.Branch;
import com.franchise_api.infrastructure.entrypoints.dto.BranchDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchDtoMapper {
    Branch toDomain(BranchDTO dto);
    BranchDTO toDto(Branch domain);
}