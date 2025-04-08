package com.franchise_api.infrastructure.mongo.adapter;

import com.franchise_api.infrastructure.mongo.entity.FranchiseEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseMongoRepository extends ReactiveMongoRepository<FranchiseEntity, String> {
}