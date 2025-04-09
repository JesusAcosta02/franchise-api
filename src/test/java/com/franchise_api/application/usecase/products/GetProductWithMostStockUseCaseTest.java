package com.franchise_api.application.usecase.products;

import com.franchise_api.domain.model.Branch;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.model.Product;
import com.franchise_api.domain.repository.FranchiseRepository;
import com.franchise_api.domain.usecase.products.GetProductWithMostStockUseCase;
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

}