package io.biker.management.biker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.service.BikerService;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/bikers")
// FIXME: Add a way to access orders by bikerId in future OrdersController
public class BikerController {
    private BikerService bikerService;

    public BikerController(BikerService bikerService) {
        this.bikerService = bikerService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public List<Biker> getAllBikers() {
        return bikerService.getAllBikers();
    }

    @GetMapping("/{id}")
    // FIXME: Allows bikers to view their own information
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public Biker getSingleBiker(@PathVariable int id) {
        return bikerService.getSingleBiker(id);
    }
}
