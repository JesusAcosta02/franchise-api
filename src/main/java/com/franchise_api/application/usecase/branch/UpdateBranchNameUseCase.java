package com.franchise_api.application.usecase.branch;

import com.franchise_api.domain.exception.BranchNotFoundException;
import com.franchise_api.domain.model.Branch;
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
                .switchIfEmpty(Mono.error(new BranchNotFoundException(branchId)))
                .map(franchise -> {
                    for (Branch branch : franchise.getBranches()) {
                        if (branch.getId().equals(branchId)) {
                            branch.setName(newName);
                            return franchise;
                        }
                    }
                    throw new BranchNotFoundException(branchId); // fallback
                })
                .flatMap(repository::save);
    }
}
