package com.franchise_api.application.usecase.products;

import com.franchise_api.domain.model.Branch;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.model.Product;
import com.franchise_api.domain.repository.FranchiseRepository;
import com.franchise_api.domain.usecase.products.UpdateProductStockUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class UpdateProductStockUseCaseTest {

    private FranchiseRepository repository;
    private UpdateProductStockUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new UpdateProductStockUseCase(repository);
    }

    @Test
    void shouldUpdateProductStock() {
        // Arrange
        String franchiseId = "f123";
        String branchId = "b123";
        String productId = "p123";
        int newStock = 50;

        Product product = new Product(productId, "Camiseta", 10);
        Branch branch = new Branch(branchId, "Sucursal Norte", List.of(product));
        Franchise franchise = new Franchise(franchiseId, "Mi Franquicia", List.of(branch));

        when(repository.findById(franchiseId)).thenReturn(Mono.just(franchise));
        when(repository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act & Assert
        StepVerifier.create(useCase.execute(franchiseId, branchId, productId, newStock))
                .expectNextMatches(result -> {
                    Product updated = result.getBranches().getFirst().getProducts().getFirst();
                    return updated.getStock().equals(newStock);
                })
                .verifyComplete();

        verify(repository).findById(franchiseId);
        verify(repository).save(any());
    }
}