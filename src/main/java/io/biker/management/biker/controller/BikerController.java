package io.biker.management.biker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.service.BikerService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/bikers")
public class BikerController {
    private BikerService bikerService;

    public BikerController(BikerService bikerService) {
        this.bikerService = bikerService;
    }

    @GetMapping()
    public List<Biker> getAllBikers() {
        return bikerService.getAllBikers();
    }

    @GetMapping("/{id}")
    public Biker getSingleBiker(@PathVariable int id) {
        return getSingleBiker(id);
    }
}
