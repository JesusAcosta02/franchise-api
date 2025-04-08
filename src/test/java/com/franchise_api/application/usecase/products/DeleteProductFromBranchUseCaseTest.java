package com.franchise_api.application.usecase.products;

import com.franchise_api.domain.model.Branch;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.model.Product;
import com.franchise_api.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class DeleteProductFromBranchUseCaseTest {

    private FranchiseRepository repository;
    private DeleteProductFromBranchUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new DeleteProductFromBranchUseCase(repository);
    }

    @Test
    void shouldDeleteProductFromBranch() {
        // Arrange
        String franchiseId = "f123";
        String branchId = "b123";
        String productId = "p123";

        Product product = new Product(productId, "Balón", 100);
        Branch branch = new Branch(branchId, "Sucursal Centro", new ArrayList<>(List.of(product)));
        Franchise franchise = new Franchise(franchiseId, "Franquicia Deportiva", List.of(branch));

        when(repository.findById(franchiseId)).thenReturn(Mono.just(franchise));
        when(repository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act & Assert
        StepVerifier.create(useCase.execute(franchiseId, branchId, productId))
                .expectNextMatches(result -> result.getBranches().get(0).getProducts().isEmpty())
                .verifyComplete();

        verify(repository).findById(franchiseId);
        verify(repository).save(any());
    }
}