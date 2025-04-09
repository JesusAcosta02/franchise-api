package com.franchise_api.infrastructure.entrypoints.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchTopProductDTO {
    private String branchId;
    private String branchName;
    private ProductDTO product;
}