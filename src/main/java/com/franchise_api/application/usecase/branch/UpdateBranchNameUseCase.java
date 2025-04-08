package com.franchise_api.application.usecase.branch;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateBranchNameUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String branchId, String newName) {
        return repository.findByBranchId(branchId)
                .map(franchise -> {
                    franchise.getBranches().forEach(branch -> {
                        if (branch.getId().equals(branchId)) {
                            branch.setName(newName);
                        }
                    });
                    return franchise;
                })
                .flatMap(repository::save);
    }
}
