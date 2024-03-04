package io.biker.management.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.constants.response.Responses;
import io.biker.management.errorHandling.responses.SuccessResponse;
import io.biker.management.store.dto.StoreCreationDTO;
import io.biker.management.store.dto.StoreReadingDTO;
import io.biker.management.store.entity.Store;
import io.biker.management.store.mapper.StoreMapper;
import io.biker.management.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Tag(name = "Stores", description = "Handles retrieval of stores and their products in a customer friendly manner.")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/stores")
public class StoreController {
    private StoreService storeService;
    private StoreMapper storeMapper;

    public StoreController(StoreService storeService, StoreMapper storeMapper) {
        this.storeService = storeService;
        this.storeMapper = storeMapper;
    }

    @Operation(description = "POST endpoint for registering a store." +
            "\n\n Can only be done by admins.", summary = "Register a store")
    @PostMapping("/stores")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of StoreCreationDTO")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public StoreReadingDTO registerStore(@Valid @RequestBody StoreCreationDTO dto) {
        Store store = storeMapper.toStore(dto);

        return storeMapper.toReadingDto(store);
    }

    @Operation(description = "GET endpoint for retrieving all stores." +
            "\n\n Can be done by any authenticated user.", summary = "Get all stores")
    @GetMapping()
    public List<StoreReadingDTO> getAllStores() {
        return storeMapper.toReadingDtos(storeService.getAllStores());
    }

    @Operation(description = "GET endpoint for retrieving a single store." +
            "\n\n Can be done by any authenticated user.", summary = "Get a single store")
    @GetMapping("/{storeId}")
    public StoreReadingDTO getSingleStore(
            @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId) {
        return storeMapper.toReadingDto(storeService.getSingleStore(storeId));
    }

    @Operation(description = "DELETE endpoint for deleting a store from the store table." +
            "\n\n Can only be done by admins.", summary = "Delete Store")
    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse deleteStore(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Store ID") @PathVariable int id) {
        storeService.deleteStore(id);
        return new SuccessResponse(Responses.STORE_DELETED);
    }
}
