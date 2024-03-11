package io.biker.management.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.constants.Roles_Const;
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
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RestController
@Tag(name = "Stores", description = "Controller for handling mappings for stores")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/stores")
public class StoreController {
    private StoreService storeService;
    private StoreMapper storeMapper;

    public StoreController(StoreService storeService, StoreMapper storeMapper) {
        this.storeService = storeService;
        this.storeMapper = storeMapper;
    }

    @Operation(description = "POST endpoint for creating a store in the table of stores." +
            "\n\n Can only be done by admins." +
            "\n\n Returns the store created as an instance of StoreReadingDTO.", summary = "Create a store")
    @PostMapping()
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of StoreCreationDTO")
    @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "')")
    public StoreReadingDTO createStore(@Valid @RequestBody StoreCreationDTO dto) {
        log.info("Recieved: POST request to /stores");
        Store store = storeService.createStore(storeMapper.toStore(dto));

        return storeMapper.toReadingDto(store);
    }

    @Operation(description = "GET endpoint for retrieving all stores." +
            "\n\n Can be done by any authenticated user." +
            "\n\n Returns the all stores as a List of StoreReadingDTO.", summary = "Get all stores")
    @GetMapping()
    public List<StoreReadingDTO> getAllStores() {
        log.info("Recieved: GET request to /stores");
        return storeMapper.toReadingDtos(storeService.getAllStores());
    }

    @Operation(description = "GET endpoint for retrieving a single store." +
            "\n\n Can be done by any authenticated user." +
            "\n\n Returns the store as an instance of StoreReadingDTO.", summary = "Get a single store")
    @GetMapping("/{storeId}")
    public StoreReadingDTO getSingleStore(
            @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId) {
        log.info("Recieved: GET request to /stores/" + storeId);
        return storeMapper.toReadingDto(storeService.getSingleStore(storeId));
    }

    @Operation(description = "DELETE endpoint for deleting a store from the table of stores." +
            "\n\n Can only be done by admins." +
            "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>", summary = "Delete a Store")
    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "')")
    public ResponseEntity<SuccessResponse> deleteStore(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Store ID") @PathVariable int id) {
        log.info("Recieved: DELETE request to /stores/" + id);
        storeService.deleteStore(id);
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(Responses.STORE_DELETED),
                HttpStatus.ACCEPTED);
    }
}
