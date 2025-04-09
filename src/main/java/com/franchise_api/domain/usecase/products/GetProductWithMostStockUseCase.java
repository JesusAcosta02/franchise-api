package com.franchise_api.domain.usecase.products;

import com.franchise_api.domain.exception.FranchiseNotFoundException;
import com.franchise_api.domain.model.Product;
import com.franchise_api.domain.repository.FranchiseRepository;
import com.franchise_api.infrastructure.entrypoints.dto.BranchTopProductDTO;
import com.franchise_api.infrastructure.entrypoints.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class GetProductWithMostStockUseCase {

    private final FranchiseRepository repository;

    public Flux<BranchTopProductDTO> execute(String franchiseId) {
        return repository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
                .flatMapMany(franchise -> Flux.fromIterable(franchise.getBranches()))
                .map(branch -> {
                    Product topProduct = branch.getProducts().stream()
                            .max(Comparator.comparing(Product::getStock))
                            .orElse(null);
                    return new BranchTopProductDTO(
                            branch.getId(),
                            branch.getName(),
                            topProduct != null ? new ProductDTO(topProduct.getId(), topProduct.getName(), topProduct.getStock()) : null
                    );
                });
    }
}