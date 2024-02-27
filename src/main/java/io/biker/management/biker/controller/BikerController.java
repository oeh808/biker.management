package io.biker.management.biker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.service.BikerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Tag(name = "Bikers")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/bikers")
// FIXME: Add a way to access orders by bikerId in future OrdersController
public class BikerController {
    private BikerService bikerService;

    public BikerController(BikerService bikerService) {
        this.bikerService = bikerService;
    }

    @Operation(description = "GET endpoint for retrieving all bikers." +
            "\n\n Can only be done by back office users.", summary = "Get all bikers")
    @GetMapping()
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public List<Biker> getAllBikers() {
        return bikerService.getAllBikers();
    }

    @Operation(description = "GET endpoint for retrieving a single biker given their id." +
            "\n\n Can only be done by back office users or the biker being retrieved.", summary = "Get single biker")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "') or " +
            "(hasAuthority('" + Roles.BIKER + "') and #id == authentication.principal.id)")
    public Biker getSingleBiker(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Biker ID") @PathVariable int id) {
        return bikerService.getSingleBiker(id);
    }
}
