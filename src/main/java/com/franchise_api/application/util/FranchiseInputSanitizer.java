package com.franchise_api.application.util;

import com.franchise_api.infrastructure.entrypoints.dto.BranchDTO;
import com.franchise_api.infrastructure.entrypoints.dto.FranchiseDTO;
import com.franchise_api.infrastructure.entrypoints.dto.ProductDTO;

import java.util.Optional;
import java.util.UUID;

public class FranchiseInputSanitizer {

    public static FranchiseDTO sanitize(FranchiseDTO dto) {
        dto.setId(generateIfNull(dto.getId()));

        Optional.ofNullable(dto.getBranches())
                .ifPresent(branches -> branches.forEach(FranchiseInputSanitizer::sanitizeBranch));

        return dto;
    }

    private static void sanitizeBranch(BranchDTO branch) {
        branch.setId(generateIfNull(branch.getId()));

        Optional.ofNullable(branch.getProducts())
                .ifPresent(products -> products.forEach(FranchiseInputSanitizer::sanitizeProduct));
    }

    private static void sanitizeProduct(ProductDTO product) {
        product.setId(generateIfNull(product.getId()));
    }

    private static String generateIfNull(String id) {
        return id == null || id.isBlank() ? UUID.randomUUID().toString() : id;
    }
}
