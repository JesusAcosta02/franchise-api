package com.franchise_api.application.usecase.products;

import static org.junit.jupiter.api.Assertions.*;

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

class UpdateProductNameUseCaseTest {

    private FranchiseRepository repository;
    private UpdateProductNameUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new UpdateProductNameUseCase(repository);
    }

    @Test
    void shouldUpdateProductName() {
        // Arrange
        String productId = "p1";
        String newName = "Producto Actualizado";

        Product product = new Product(productId, "Nombre Viejo", 10);
        Branch branch = new Branch("b1", "Sucursal", List.of(product));
        Franchise franchise = new Franchise("f1", "Franquicia", List.of(branch));

        when(repository.findByProductId(productId)).thenReturn(Mono.just(franchise));
        when(repository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act & Assert
        StepVerifier.create(useCase.execute(productId, newName))
                .expectNextMatches(result ->
                        result.getBranches().get(0).getProducts().get(0).getName().equals(newName)
                )
                .verifyComplete();

        verify(repository).findByProductId(productId);
        verify(repository).save(any());
    }
}