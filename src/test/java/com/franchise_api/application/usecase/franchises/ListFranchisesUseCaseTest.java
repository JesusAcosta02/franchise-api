package com.franchise_api.application.usecase.franchises;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import com.franchise_api.domain.usecase.franchises.ListFranchisesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class ListFranchisesUseCaseTest {

    private FranchiseRepository repository;
    private ListFranchisesUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepository.class);
        useCase = new ListFranchisesUseCase(repository);
    }

    @Test
    void shouldListFranchises() {
        Franchise f1 = new Franchise("f1", "Franquicia A", null);
        Franchise f2 = new Franchise("f2", "Franquicia B", null);

        when(repository.findAll()).thenReturn(Flux.fromIterable(List.of(f1, f2)));

        StepVerifier.create(useCase.execute(null))
                .expectNext(f1)
                .expectNext(f2)
                .verifyComplete();


        verify(repository).findAll();
    }
}