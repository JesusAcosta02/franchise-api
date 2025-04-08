package com.franchise_api.domain.exception;

public class FranchiseNotFoundException extends RuntimeException {
    public FranchiseNotFoundException(String id) {
        super("Franchise not found with id: " + id);
    }
}