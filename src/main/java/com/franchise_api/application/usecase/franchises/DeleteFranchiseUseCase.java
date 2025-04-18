package com.franchise_api.application.usecase.franchises;

import com.franchise_api.application.usecase.UseCase;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class DeleteFranchiseUseCase implements UseCase<String, Mono<Void>> {

    private final FranchiseRepository repository;

    @Override
    public Mono<Void> execute(String id) {
        return repository.deleteById(id);
    }
}