package com.franchise_api.application.usecase.products;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateProductStockUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String franchiseId, String branchId, String productId, Integer newStock) {
        return repository.findById(franchiseId)
                .map(franchise -> {
                    franchise.getBranches().forEach(branch -> {
                        if (branch.getId().equals(branchId)) {
                            branch.getProducts().forEach(product -> {
                                if (product.getId().equals(productId)) {
                                    product.setStock(newStock);
                                }
                            });
                        }
                    });
                    return franchise;
                })
                .flatMap(repository::save);
    }
}
