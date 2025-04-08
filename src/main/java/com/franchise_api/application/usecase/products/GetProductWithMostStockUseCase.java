package com.franchise_api.application.usecase.products;

import com.franchise_api.domain.model.Branch;
import com.franchise_api.domain.model.Product;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetProductWithMostStockUseCase {

    private final FranchiseRepository repository;

    public Mono<Product> execute(String branchId) {
        return repository.findByBranchId(branchId)
                .flatMap(franchise -> {
                    Optional<Branch> branchOpt = franchise.getBranches().stream()
                            .filter(branch -> branch.getId().equals(branchId))
                            .findFirst();

                    if (branchOpt.isPresent()) {
                        return Mono.justOrEmpty(
                                branchOpt.get().getProducts().stream()
                                        .max(Comparator.comparing(Product::getStock))
                        );
                    } else {
                        return Mono.empty();
                    }
                });
    }
}