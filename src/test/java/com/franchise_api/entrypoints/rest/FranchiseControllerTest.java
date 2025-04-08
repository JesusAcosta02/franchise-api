package com.franchise_api.entrypoints.rest;

import com.franchise_api.application.usecase.branch.AddBranchToFranchiseUseCase;
import com.franchise_api.application.usecase.branch.UpdateBranchNameUseCase;
import com.franchise_api.application.usecase.franchises.*;
import com.franchise_api.application.usecase.products.DeleteProductFromBranchUseCase;
import com.franchise_api.application.usecase.products.GetProductWithMostStockUseCase;
import com.franchise_api.application.usecase.products.UpdateProductNameUseCase;
import com.franchise_api.application.usecase.products.UpdateProductStockUseCase;
import com.franchise_api.domain.model.Franchise;
import com.franchise_api.entrypoints.dto.FranchiseDTO;
import com.franchise_api.entrypoints.dto.UpdateNameRequest;
import com.franchise_api.entrypoints.dto.mappers.BranchDtoMapper;
import com.franchise_api.entrypoints.dto.mappers.FranchiseDtoMapper;
import com.franchise_api.entrypoints.dto.mappers.ProductDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = FranchiseController.class)
class FranchiseControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SaveFranchiseUseCase saveFranchiseUseCase;
    @MockBean
    private GetFranchiseByIdUseCase getFranchiseByIdUseCase;
    @MockBean
    private ListFranchisesUseCase listFranchisesUseCase;
    @MockBean
    private DeleteFranchiseUseCase deleteFranchiseUseCase;
    @MockBean
    private UpdateBranchNameUseCase updateBranchNameUseCase;
    @MockBean
    private UpdateProductNameUseCase updateProductNameUseCase;
    @MockBean
    private AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;
    @MockBean
    private DeleteProductFromBranchUseCase deleteProductFromBranchUseCase;
    @MockBean
    private UpdateProductStockUseCase updateProductStockUseCase;
    @MockBean
    private GetProductWithMostStockUseCase getProductWithMostStockUseCase;
    @MockBean
    private UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    @MockBean
    private FranchiseDtoMapper franchiseDtoMapper;
    @MockBean
    private ProductDtoMapper productDtoMapper;
    @MockBean
    private BranchDtoMapper branchDtoMapper;

    private Franchise franchise;
    private FranchiseDTO franchiseDTO;

    @BeforeEach
    void setUp() {
        franchise = new Franchise("1", "Franquicia X", List.of());
        franchiseDTO = new FranchiseDTO();
    }

    @Test
    void shouldCreateFranchise() {
        when(franchiseDtoMapper.toDomain(any())).thenReturn(franchise);
        when(saveFranchiseUseCase.execute(franchise)).thenReturn(Mono.just(franchise));
        when(franchiseDtoMapper.toDto(franchise)).thenReturn(franchiseDTO);

        webTestClient.post()
                .uri("/api/franchises")
                .bodyValue(franchiseDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FranchiseDTO.class)
                .isEqualTo(franchiseDTO);
    }

    @Test
    void shouldGetFranchiseById() {
        when(getFranchiseByIdUseCase.execute("1")).thenReturn(Mono.just(franchise));
        when(franchiseDtoMapper.toDto(franchise)).thenReturn(franchiseDTO);

        webTestClient.get()
                .uri("/api/franchises/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(FranchiseDTO.class)
                .isEqualTo(franchiseDTO);
    }

    @Test
    void shouldListFranchises() {
        FranchiseDTO d2 = new FranchiseDTO();
        Franchise f2 = new Franchise("2", "F2", List.of());

        when(listFranchisesUseCase.execute(null)).thenReturn(Flux.just(franchise, f2));
        when(franchiseDtoMapper.toDto(franchise)).thenReturn(franchiseDTO);
        when(franchiseDtoMapper.toDto(f2)).thenReturn(d2);

        webTestClient.get()
                .uri("/api/franchises")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FranchiseDTO.class)
                .contains(franchiseDTO, d2);
    }

    @Test
    void shouldDeleteFranchise() {
        when(deleteFranchiseUseCase.execute("1")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/franchises/1")
                .exchange()
                .expectStatus().isOk();
    }


}
