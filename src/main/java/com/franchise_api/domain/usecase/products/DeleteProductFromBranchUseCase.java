package com.franchise_api.domain.usecase.products;

import com.franchise_api.domain.exception.BranchNotFoundException;
import com.franchise_api.domain.exception.FranchiseNotFoundException;
import com.franchise_api.domain.exception.ProductNotFoundException;
import com.franchise_api.domain.model.Branch;
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
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
                .map(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new BranchNotFoundException(branchId));

                    boolean removed = branch.getProducts().removeIf(p -> p.getId().equals(productId));

                    if (!removed) {
                        throw new ProductNotFoundException(productId);
                    }

                    return franchise;
                })
                .flatMap(repository::save);
    }
}
