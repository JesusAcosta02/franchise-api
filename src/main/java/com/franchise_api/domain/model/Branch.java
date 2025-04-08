package com.franchise_api.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    private String id;
    private String name;
    private List<Product> products;
}