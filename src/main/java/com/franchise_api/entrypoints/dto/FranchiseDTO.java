package com.franchise_api.entrypoints.dto;

import lombok.Data;

import java.util.List;

@Data
public class FranchiseDTO {
    private String id;
    private String name;
    private List<BranchDTO> branches;
}