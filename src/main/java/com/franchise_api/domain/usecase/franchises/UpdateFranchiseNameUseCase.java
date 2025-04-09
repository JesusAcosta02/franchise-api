package com.franchise_api.domain.usecase.franchises;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateFranchiseNameUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String franchiseId, String newName) {
        return repository.findById(franchiseId)
                .map(franchise -> {
                    franchise.setName(newName);
                    return franchise;
                })
                .flatMap(repository::save);
    }
}
