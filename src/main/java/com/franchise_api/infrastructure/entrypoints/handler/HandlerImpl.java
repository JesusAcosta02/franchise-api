package com.franchise_api.infrastructure.entrypoints.handler;


import com.franchise_api.application.util.FranchiseInputSanitizer;
import com.franchise_api.domain.usecase.branch.AddBranchToFranchiseUseCase;
import com.franchise_api.domain.usecase.branch.UpdateBranchNameUseCase;
import com.franchise_api.domain.usecase.franchises.*;
import com.franchise_api.domain.usecase.products.*;
import com.franchise_api.infrastructure.entrypoints.dto.*;
import com.franchise_api.infrastructure.entrypoints.dto.mappers.BranchDtoMapper;
import com.franchise_api.infrastructure.entrypoints.dto.mappers.FranchiseDtoMapper;
import com.franchise_api.infrastructure.entrypoints.dto.mappers.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HandlerImpl {
    private final SaveFranchiseUseCase saveFranchiseUseCase;
    private final GetFranchiseByIdUseCase getFranchiseByIdUseCase;
    private final ListFranchisesUseCase listFranchisesUseCase;
    private final DeleteFranchiseUseCase deleteFranchiseUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;
    private final AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;
    private final DeleteProductFromBranchUseCase deleteProductFromBranchUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final GetProductWithMostStockUseCase getProductWithMostStockUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final AddProductToBranchUseCase addProductToBranchUseCase;
    private final ProductDtoMapper productDtoMapper;
    private final FranchiseDtoMapper franchiseDtoMapper;
    private final BranchDtoMapper branchDtoMapper;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(FranchiseDTO.class)
                .map(FranchiseInputSanitizer::sanitize)
                .map(franchiseDtoMapper::toDomain)
                .flatMap(saveFranchiseUseCase::execute)
                .map(franchiseDtoMapper::toDto)
                .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        return getFranchiseByIdUseCase.execute(id)
                .map(franchiseDtoMapper::toDto)
                .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
    }

    public Mono<ServerResponse> listAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(listFranchisesUseCase.execute(null).map(franchiseDtoMapper::toDto), FranchiseDTO.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return deleteFranchiseUseCase.execute(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        String name = request.queryParam("name").orElseThrow(() -> new IllegalArgumentException("Missing name param"));
        return updateBranchNameUseCase.execute(branchId, name)
                .map(franchiseDtoMapper::toDto)
                .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
    }

    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        String productId = request.pathVariable("productId");
        String name = request.queryParam("name").orElseThrow(() -> new IllegalArgumentException("Missing name param"));
        return updateProductNameUseCase.execute(productId, name)
                .map(franchiseDtoMapper::toDto)
                .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
    }

    public Mono<ServerResponse> addBranch(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return request.bodyToMono(BranchDTO.class)
                .map(branchDtoMapper::toDomain)
                .flatMap(branch -> addBranchToFranchiseUseCase.execute(franchiseId, branch))
                .map(franchiseDtoMapper::toDto)
                .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
    }

    public Mono<ServerResponse> deleteProductFromBranch(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        return deleteProductFromBranchUseCase.execute(franchiseId, branchId, productId)
                .map(franchiseDtoMapper::toDto)
                .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
    }

    public Mono<ServerResponse> updateProductStock(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        return request.bodyToMono(UpdateStockRequest.class)
                .flatMap(r -> updateProductStockUseCase.execute(franchiseId, branchId, productId, r.getStock()))
                .map(franchiseDtoMapper::toDto)
                .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
    }

    public Mono<ServerResponse> getProductWithMostStock(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return getProductWithMostStockUseCase.execute(franchiseId)
                .collectList()
                .flatMap(dtoList -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dtoList));
    }

    public Mono<ServerResponse> updateFranchiseName(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return request.bodyToMono(UpdateNameRequest.class)
                .flatMap(r -> updateFranchiseNameUseCase.execute(franchiseId, r.getName()))
                .map(franchiseDtoMapper::toDto)
                .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto));
    }

    public Mono<ServerResponse> addProductToBranch(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        return request.bodyToMono(ProductDTO.class)
                .map(productDtoMapper::toDomain)
                .flatMap(product ->
                        addProductToBranchUseCase.execute(branchId, product)
                                .map(franchiseDtoMapper::toDto)
                                .flatMap(dto -> ServerResponse.ok().bodyValue(dto))
                );
    }

}
