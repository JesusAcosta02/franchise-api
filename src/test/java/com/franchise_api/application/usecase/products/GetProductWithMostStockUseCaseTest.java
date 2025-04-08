package com.franchise_api.application.usecase.products;

import com.franchise_api.domain.model.Branch;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.model.Product;
import com.franchise_api.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class GetProductWithMostStockUseCaseTest {

    private FranchiseRepository repository;
    private GetProductWithMostStockUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new GetProductWithMostStockUseCase(repository);
    }

    @Test
    void shouldReturnProductWithHighestStock() {
        // Arrange
        String branchId = "b123";

        Product p1 = new Product("p1", "Zapatos", 20);
        Product p2 = new Product("p2", "Balón", 60);
        Branch branch = new Branch(branchId, "Sucursal", List.of(p1, p2));
        Franchise franchise = new Franchise("f001", "Franquicia Deportiva", List.of(branch));

        when(repository.findByBranchId(branchId)).thenReturn(Mono.just(franchise));

        // Act & Assert
        StepVerifier.create(useCase.execute(branchId))
                .expectNextMatches(product -> product.getId().equals("p2") && product.getStock().equals(60))
                .verifyComplete();

        verify(repository).findByBranchId(branchId);
    }
}