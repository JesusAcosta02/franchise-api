package com.franchise_api.entrypoints.rest;

import com.franchise_api.application.usecase.*;
import com.franchise_api.application.util.FranchiseInputSanitizer;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.entrypoints.dto.FranchiseDTO;
import com.franchise_api.entrypoints.dto.mappers.FranchiseDtoMapper;
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
    private final FranchiseDtoMapper mapper;

    @PostMapping
    public Mono<FranchiseDTO> create(@RequestBody FranchiseDTO dto) {
        FranchiseDTO sanitized = FranchiseInputSanitizer.sanitize(dto);
        Franchise domain = mapper.toDomain(sanitized);
        return saveFranchiseUseCase.execute(domain)
                .map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<FranchiseDTO> getById(@PathVariable String id) {
        return getFranchiseByIdUseCase.execute(id)
                .map(mapper::toDto);
    }

    @GetMapping
    public Flux<FranchiseDTO> listAll() {
        return listFranchisesUseCase.execute(null)
                .map(mapper::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return deleteFranchiseUseCase.execute(id);
    }

    @PatchMapping("/branches/{branchId}/name")
    public Mono<FranchiseDTO> updateBranchName(@PathVariable String branchId, @RequestParam String name) {
        return updateBranchNameUseCase.execute(branchId, name)
                .map(mapper::toDto);
    }

    @PatchMapping("/products/{productId}/name")
    public Mono<FranchiseDTO> updateProductName(@PathVariable String productId, @RequestParam String name) {
        return updateProductNameUseCase.execute(productId, name)
                .map(mapper::toDto);
    }
}