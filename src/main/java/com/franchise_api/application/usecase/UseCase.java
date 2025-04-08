package com.franchise_api.application.usecase;

public interface UseCase<I, O> {
    O execute(I input);
}