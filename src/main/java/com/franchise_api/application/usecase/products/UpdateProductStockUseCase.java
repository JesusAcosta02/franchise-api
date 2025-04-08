package com.franchise_api.application.usecase.products;

import com.franchise_api.domain.exception.BranchNotFoundException;
import com.franchise_api.domain.exception.FranchiseNotFoundException;
import com.franchise_api.domain.exception.ProductNotFoundException;
import com.franchise_api.domain.model.Branch;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.model.Product;
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
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
                .map(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new BranchNotFoundException(branchId));

                    Product product = branch.getProducts().stream()
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElseThrow(() -> new ProductNotFoundException(productId));

                    product.setStock(newStock);
                    return franchise;
                })
                .flatMap(repository::save);
    }
}
