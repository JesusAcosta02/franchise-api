package com.franchise_api.application.usecase.franchises;

import com.franchise_api.application.usecase.UseCase;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SaveFranchiseUseCase implements UseCase<Franchise, Mono<Franchise>> {


    private final FranchiseRepository repository;

    @Override
    public Mono<Franchise> execute(Franchise franchise) {
        return repository.save(franchise);
    }
}