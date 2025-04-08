package com.franchise_api.infrastructure.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchEntity {
    private String id;
    private String name;
    private List<ProductEntity> products;
}