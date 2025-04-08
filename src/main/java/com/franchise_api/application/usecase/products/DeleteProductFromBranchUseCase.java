package com.franchise_api.application.usecase.products;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DeleteProductFromBranchUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String franchiseId, String branchId, String productId) {
        return repository.findById(franchiseId)
                .map(franchise -> {
                    franchise.getBranches().forEach(branch -> {
                        if (branch.getId().equals(branchId)) {
                            branch.getProducts().removeIf(product -> product.getId().equals(productId));
                        }
                    });
                    return franchise;
                })
                .flatMap(repository::save);
    }
}
