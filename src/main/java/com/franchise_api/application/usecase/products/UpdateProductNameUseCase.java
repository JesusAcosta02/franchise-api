package com.franchise_api.application.usecase.products;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateProductNameUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String productId, String newName) {
        return repository.findByProductId(productId)
                .map(franchise -> {
                    franchise.getBranches().forEach(branch ->
                            branch.getProducts().forEach(product -> {
                                if (product.getId().equals(productId)) {
                                    product.setName(newName);
                                }
                            }));
                    return franchise;
                })
                .flatMap(repository::save);
    }
}
