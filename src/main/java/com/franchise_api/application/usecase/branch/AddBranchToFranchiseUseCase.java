package com.franchise_api.application.usecase.branch;

import com.franchise_api.domain.model.Branch;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddBranchToFranchiseUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String franchiseId, Branch newBranch) {
        if (newBranch.getId() == null || newBranch.getId().isBlank()) {
            newBranch.setId(UUID.randomUUID().toString());
        }

        return repository.findById(franchiseId)
                .map(franchise -> {
                    franchise.getBranches().add(newBranch);
                    return franchise;
                })
                .flatMap(repository::save);
    }
}
