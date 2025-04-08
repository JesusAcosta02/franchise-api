package com.franchise_api.entrypoints.exception;

import com.franchise_api.domain.exception.BranchNotFoundException;
import com.franchise_api.domain.exception.FranchiseNotFoundException;
import com.franchise_api.domain.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FranchiseNotFoundException.class)
    public Mono<ServerResponse> handleFranchiseNotFound(FranchiseNotFoundException ex, ServerWebExchange exchange) {
        return buildErrorResponse(exchange, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public Mono<ServerResponse> handleGenericException(ServerWebExchange exchange) {
        return buildErrorResponse(exchange, "Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Mono<ServerResponse> buildErrorResponse(ServerWebExchange exchange, String message, HttpStatus status) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .path(exchange.getRequest().getPath().value())
                .status(status.value())
                .build();

        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    @ExceptionHandler(BranchNotFoundException.class)
    public Mono<ServerResponse> handleBranchNotFound(BranchNotFoundException ex, ServerWebExchange exchange) {
        return buildErrorResponse(exchange, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public Mono<ServerResponse> handleProductNotFound(ProductNotFoundException ex, ServerWebExchange exchange) {
        return buildErrorResponse(exchange, ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}