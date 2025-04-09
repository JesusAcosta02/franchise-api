package com.franchise_api.domain.usecase.products;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.model.Product;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddProductToBranchUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String branchId, Product product) {
        product.setId(UUID.randomUUID().toString());
        return repository.findByBranchId(branchId)
                .map(franchise -> {
                    franchise.getBranches().stream()
                            .filter(branch -> branch.getId().equals(branchId))
                            .findFirst()
                            .ifPresent(branch -> branch.getProducts().add(product));
                    return franchise;
                })
                .flatMap(repository::save);
    }
}
