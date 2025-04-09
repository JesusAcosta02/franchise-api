package com.franchise_api.application.usecase.branch;

import com.franchise_api.domain.model.Branch;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import com.franchise_api.domain.usecase.branch.UpdateBranchNameUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class UpdateBranchNameUseCaseTest {

    private FranchiseRepository repository;
    private UpdateBranchNameUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new UpdateBranchNameUseCase(repository);
    }

    @Test
    void shouldUpdateBranchName() {
        // Arrange
        String branchId = "b001";
        String newName = "Sucursal Sur";

        Branch branch = new Branch(branchId, "Sucursal Antigua", List.of());
        Franchise franchise = new Franchise("f001", "Franquicia X", List.of(branch));

        when(repository.findByBranchId(branchId)).thenReturn(Mono.just(franchise));
        when(repository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act & Assert
        StepVerifier.create(useCase.execute(branchId, newName))
                .expectNextMatches(result -> result.getBranches().getFirst().getName().equals(newName))
                .verifyComplete();

        verify(repository).findByBranchId(branchId);
        verify(repository).save(any());
    }
}