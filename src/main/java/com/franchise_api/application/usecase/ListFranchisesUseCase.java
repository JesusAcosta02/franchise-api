package com.franchise_api.application.usecase;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


@Component
@RequiredArgsConstructor
public class ListFranchisesUseCase implements UseCase<Void, Flux<Franchise>> {

    private final FranchiseRepository repository;

    @Override
    public Flux<Franchise> execute(Void unused) {
        return repository.findAll();
    }
}