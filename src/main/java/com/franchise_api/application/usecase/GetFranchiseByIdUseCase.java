package com.franchise_api.application.usecase;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetFranchiseByIdUseCase implements UseCase<String, Mono<Franchise>> {

    private final FranchiseRepository repository;

    @Override
    public Mono<Franchise> execute(String id) {
        return repository.findById(id);
    }
}