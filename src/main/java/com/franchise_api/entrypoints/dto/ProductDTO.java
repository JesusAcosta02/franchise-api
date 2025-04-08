package com.franchise_api.entrypoints.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String id;
    private String name;
    private Integer stock;
}