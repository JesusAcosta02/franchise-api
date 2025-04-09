package com.franchise_api.domain.usecase;

public interface UseCase<I, O> {
    O execute(I input);
}