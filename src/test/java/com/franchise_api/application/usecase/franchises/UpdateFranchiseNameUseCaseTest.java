package com.franchise_api.application.usecase.franchises;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class UpdateFranchiseNameUseCaseTest {

    private FranchiseRepository repository;
    private UpdateFranchiseNameUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new UpdateFranchiseNameUseCase(repository);
    }

    @Test
    void shouldUpdateFranchiseName() {
        Franchise franchise = new Franchise("f001", "Antiguo Nombre", null);

        when(repository.findById("f001")).thenReturn(Mono.just(franchise));
        when(repository.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(useCase.execute("f001", "Nuevo Nombre"))
                .expectNextMatches(f -> f.getName().equals("Nuevo Nombre"))
                .verifyComplete();

        verify(repository).findById("f001");
        verify(repository).save(any());
    }
}