package com.franchise_api.application.usecase.franchises;

import static org.junit.jupiter.api.Assertions.*;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class SaveFranchiseUseCaseTest {

    private FranchiseRepository repository;
    private SaveFranchiseUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new SaveFranchiseUseCase(repository);
    }

    @Test
    void shouldSaveFranchise() {
        Franchise franchise = new Franchise("f001", "Nueva Franquicia", null);

        when(repository.save(franchise)).thenReturn(Mono.just(franchise));

        StepVerifier.create(useCase.execute(franchise))
                .expectNext(franchise)
                .verifyComplete();

        verify(repository).save(franchise);
    }
}