package com.franchise_api.application.usecase.franchises;

import static org.junit.jupiter.api.Assertions.*;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class GetFranchiseByIdUseCaseTest {

    private FranchiseRepository repository;
    private GetFranchiseByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new GetFranchiseByIdUseCase(repository);
    }

    @Test
    void shouldReturnFranchiseById() {
        Franchise franchise = new Franchise("f123", "Franquicia Central", null);
        when(repository.findById("f123")).thenReturn(Mono.just(franchise));

        StepVerifier.create(useCase.execute("f123"))
                .expectNext(franchise)
                .verifyComplete();

        verify(repository).findById("f123");
    }
}