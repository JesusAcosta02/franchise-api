package com.franchise_api.application.usecase.franchises;

import com.franchise_api.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class DeleteFranchiseUseCaseTest {

    private FranchiseRepository repository;
    private DeleteFranchiseUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new DeleteFranchiseUseCase(repository);
    }

    @Test
    void shouldDeleteFranchise() {
        when(repository.deleteById("f123")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute("f123"))
                .verifyComplete();

        verify(repository).deleteById("f123");
    }
}