package com.franchise_api.infrastructure.entrypoints.dto;

import lombok.Data;

import java.util.List;

@Data
public class BranchDTO {
    private String id;
    private String name;
    private List<ProductDTO> products;
}