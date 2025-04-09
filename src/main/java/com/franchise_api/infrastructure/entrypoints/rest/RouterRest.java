package com.franchise_api.infrastructure.entrypoints.rest;

import com.franchise_api.infrastructure.entrypoints.handler.HandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> franchiseRoutes(HandlerImpl handler) {
        return RouterFunctions.route()
                .POST("/api/franchises", handler::create)
                .GET("/api/franchises", handler::listAll)
                .GET("/api/franchises/{id}", handler::getById)
                .DELETE("/api/franchises/{id}", handler::delete)
                .PATCH("/api/franchises/{franchiseId}/name", handler::updateFranchiseName)
                .PATCH("/api/franchises/{franchiseId}/branches", handler::addBranch)
                .PATCH("/api/franchises/branches/{branchId}/name", handler::updateBranchName)
                .PATCH("/api/franchises/products/{productId}/name", handler::updateProductName)
                .PATCH("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock", handler::updateProductStock)
                .DELETE("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}", handler::deleteProductFromBranch)
                .GET("/api/franchises/{franchiseId}/products/most-stock", handler::getProductWithMostStock)
                .POST("/api/franchises/branches/{branchId}/products", handler::addProductToBranch)
                .build();
    }
}