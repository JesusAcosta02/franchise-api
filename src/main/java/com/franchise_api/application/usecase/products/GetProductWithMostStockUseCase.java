package com.franchise_api.application.usecase.products;

import com.franchise_api.domain.exception.BranchNotFoundException;
import com.franchise_api.domain.exception.ProductNotFoundException;
import com.franchise_api.domain.model.Branch;
import com.franchise_api.domain.model.Product;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class GetProductWithMostStockUseCase {

    private final FranchiseRepository repository;

    public Mono<Product> execute(String branchId) {
        return repository.findByBranchId(branchId)
                .switchIfEmpty(Mono.error(new BranchNotFoundException(branchId)))
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new BranchNotFoundException(branchId));

                    return Mono.justOrEmpty(
                            branch.getProducts().stream()
                                    .max(Comparator.comparing(Product::getStock))
                                    .orElseThrow(() -> new ProductNotFoundException("No products found in branch " + branchId))
                    );
                });
    }
}