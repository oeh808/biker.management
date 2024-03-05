package io.biker.management.user.analysis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.biker.service.BikerService;
import io.biker.management.user.analysis.data.BikerAnalysis;
import io.biker.management.user.analysis.data.SystemAnalysis;
import io.biker.management.user.analysis.data.SystemReport;
import io.biker.management.user.analysis.service.AnalysisServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Tag(name = "Analysis")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/analysis")
public class AnalysisController {
    private AnalysisServiceImpl analysisService;
    private BikerService bikerService;

    public AnalysisController(AnalysisServiceImpl analysisService, BikerService bikerService) {
        this.analysisService = analysisService;
        this.bikerService = bikerService;
    }

    @Operation(description = "GET endpoint for retrieving an analysis of a biker's performance given their id." +
            "\n\n Can only be done by back office users.", summary = "Get an analysis of a single biker's performance")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public BikerAnalysis analyzeBikerPerformance(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Biker ID") @PathVariable int id) {
        return analysisService.getBikerAnalysis(bikerService.getSingleBiker(id));
    }

    @Operation(description = "GET endpoint for retrieving an a report of the system's performance." +
            "\n\n Can only be done by admins.", summary = "Get a report of the system as a whole")
    @GetMapping()
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public SystemAnalysis analyzeSystemPerformance() {
        return analysisService.getSystemAnalysis(bikerService.getAllBikers());
    }

    @Operation(description = "GET endpoint for generating a system report with suggestions included." +
            "\n\n Can only be done by back office users.", summary = "Get suggestions for improving system")
    @GetMapping("/report")
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public SystemReport generateReport() {
        return analysisService.generateReport(bikerService.getAllBikers());
    }
}
