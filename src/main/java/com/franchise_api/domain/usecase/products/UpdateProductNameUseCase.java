package com.franchise_api.domain.usecase.products;

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
public class UpdateProductNameUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String productId, String newName) {
        return repository.findByProductId(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(productId)))
                .map(franchise -> {
                    for (Branch branch : franchise.getBranches()) {
                        for (Product product : branch.getProducts()) {
                            if (product.getId().equals(productId)) {
                                product.setName(newName);
                                return franchise;
                            }
                        }
                    }
                    throw new ProductNotFoundException(productId);
                })
                .flatMap(repository::save);
    }
}
