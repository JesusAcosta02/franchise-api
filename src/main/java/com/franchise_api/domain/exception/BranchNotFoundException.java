package com.franchise_api.domain.exception;


public class BranchNotFoundException extends RuntimeException {
    public BranchNotFoundException(String id) {
        super("Branch not found with id: " + id);
    }
}