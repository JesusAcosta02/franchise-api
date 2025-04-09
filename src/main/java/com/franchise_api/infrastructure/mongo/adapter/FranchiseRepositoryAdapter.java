package com.franchise_api.infrastructure.mongo.adapter;

import com.franchise_api.domain.model.Franchise;
import com.franchise_api.domain.repository.FranchiseRepository;
import com.franchise_api.infrastructure.entrypoints.mapper.FranchiseEntityMapper;
import com.franchise_api.infrastructure.mongo.entity.FranchiseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepository {

    private final FranchiseMongoRepository mongoRepository;
    private final FranchiseEntityMapper mapper;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        FranchiseEntity entity = mapper.toEntity(franchise);
        return mongoRepository.save(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return mongoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return mongoRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return mongoRepository.deleteById(id);
    }

    @Override
    public Mono<Franchise> findByBranchId(String branchId) {
        return mongoRepository.findAll()
                .filter(entity ->
                        entity.getBranches().stream()
                                .anyMatch(branch -> branch.getId().equals(branchId))
                )
                .next()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Franchise> findByProductId(String productId) {
        return mongoRepository.findAll()
                .filter(entity ->
                        entity.getBranches().stream()
                                .anyMatch(branch -> branch.getProducts().stream()
                                        .anyMatch(product -> product.getId().equals(productId)))
                )
                .next()
                .map(mapper::toDomain);
    }
}
