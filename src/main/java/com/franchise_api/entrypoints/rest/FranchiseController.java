package com.franchise_api.entrypoints.rest;

import com.franchise_api.application.usecase.branch.AddBranchToFranchiseUseCase;
import com.franchise_api.application.usecase.branch.UpdateBranchNameUseCase;
import com.franchise_api.application.usecase.franchises.*;
import com.franchise_api.application.usecase.products.DeleteProductFromBranchUseCase;
import com.franchise_api.application.usecase.products.GetProductWithMostStockUseCase;
import com.franchise_api.application.usecase.products.UpdateProductNameUseCase;
import com.franchise_api.application.usecase.products.UpdateProductStockUseCase;
import com.franchise_api.application.util.FranchiseInputSanitizer;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.entrypoints.dto.*;
import com.franchise_api.entrypoints.dto.mappers.BranchDtoMapper;
import com.franchise_api.entrypoints.dto.mappers.FranchiseDtoMapper;
import com.franchise_api.entrypoints.dto.mappers.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

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
    private final ProductDtoMapper productDtoMapper;
    private final FranchiseDtoMapper franchiseDtoMapper;
    private final BranchDtoMapper branchDtoMapper;

    @PostMapping
    public Mono<FranchiseDTO> create(@RequestBody FranchiseDTO dto) {
        FranchiseDTO sanitized = FranchiseInputSanitizer.sanitize(dto);
        Franchise domain = franchiseDtoMapper.toDomain(sanitized);
        return saveFranchiseUseCase.execute(domain)
                .map(franchiseDtoMapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<FranchiseDTO> getById(@PathVariable String id) {
        return getFranchiseByIdUseCase.execute(id)
                .map(franchiseDtoMapper::toDto);
    }

    @GetMapping
    public Flux<FranchiseDTO> listAll() {
        return listFranchisesUseCase.execute(null)
                .map(franchiseDtoMapper::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return deleteFranchiseUseCase.execute(id);
    }

    @PatchMapping("/branches/{branchId}/name")
    public Mono<FranchiseDTO> updateBranchName(@PathVariable String branchId, @RequestParam String name) {
        return updateBranchNameUseCase.execute(branchId, name)
                .map(franchiseDtoMapper::toDto);
    }

    @PatchMapping("/products/{productId}/name")
    public Mono<FranchiseDTO> updateProductName(@PathVariable String productId, @RequestParam String name) {
        return updateProductNameUseCase.execute(productId, name)
                .map(franchiseDtoMapper::toDto);
    }

    @PatchMapping("/{franchiseId}/branches")
    public Mono<FranchiseDTO> addBranch(@PathVariable String franchiseId,
                                        @RequestBody BranchDTO newBranchDTO) {
        return addBranchToFranchiseUseCase
                .execute(franchiseId, branchDtoMapper.toDomain(newBranchDTO))
                .map(franchiseDtoMapper::toDto);
    }

    @DeleteMapping("/{franchiseId}/branches/{branchId}/products/{productId}")
    public Mono<FranchiseDTO> deleteProductFromBranch(@PathVariable String franchiseId,
                                                      @PathVariable String branchId,
                                                      @PathVariable String productId) {
        return deleteProductFromBranchUseCase
                .execute(franchiseId, branchId, productId)
                .map(franchiseDtoMapper::toDto);
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}/products/{productId}/stock")
    public Mono<FranchiseDTO> updateProductStock(@PathVariable String franchiseId,
                                                 @PathVariable String branchId,
                                                 @PathVariable String productId,
                                                 @RequestBody UpdateStockRequest request) {
        return updateProductStockUseCase
                .execute(franchiseId, branchId, productId, request.getStock())
                .map(franchiseDtoMapper::toDto);
    }

    @GetMapping("/branches/{branchId}/products/most-stock")
    public Mono<ProductDTO> getProductWithMostStock(@PathVariable String branchId) {
        return getProductWithMostStockUseCase
                .execute(branchId)
                .map(productDtoMapper::toDto);
    }

    @PatchMapping("/{franchiseId}/name")
    public Mono<FranchiseDTO> updateFranchiseName(@PathVariable String franchiseId,
                                                  @RequestBody UpdateNameRequest request) {
        return updateFranchiseNameUseCase
                .execute(franchiseId, request.getName())
                .map(franchiseDtoMapper::toDto);
    }


}