package com.franchise_api.application.usecase.branch;

import com.franchise_api.domain.model.Branch;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class AddBranchToFranchiseUseCaseTest {

    private FranchiseRepository repository;
    private AddBranchToFranchiseUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new AddBranchToFranchiseUseCase(repository);
    }

    @Test
    void shouldAddBranchToFranchise() {
        // Arrange
        String franchiseId = "f123";
        Branch newBranch = new Branch("b001", "Nueva Sucursal", List.of());

        Franchise franchise = new Franchise(franchiseId, "Franquicia X", new ArrayList<>());

        when(repository.findById(franchiseId)).thenReturn(Mono.just(franchise));
        when(repository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act & Assert
        StepVerifier.create(useCase.execute(franchiseId, newBranch))
                .expectNextMatches(result ->
                        result.getBranches().stream().anyMatch(b -> b.getId().equals("b001"))
                )
                .verifyComplete();

        verify(repository).findById(franchiseId);
        verify(repository).save(any());
    }
}