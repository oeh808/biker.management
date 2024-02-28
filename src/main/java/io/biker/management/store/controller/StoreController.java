package io.biker.management.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.store.dto.StoreReadingDTO;
import io.biker.management.store.mapper.StoreMapper;
import io.biker.management.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Tag(name = "Stores")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/stores")
public class StoreController {
    private StoreService storeService;
    private StoreMapper storeMapper;

    public StoreController(StoreService storeService, StoreMapper storeMapper) {
        this.storeService = storeService;
        this.storeMapper = storeMapper;
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
}
