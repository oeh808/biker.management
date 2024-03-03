package io.biker.management.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.service.BackOfficeService;
import io.biker.management.user.analysis.AnalysisService;
import io.biker.management.user.analysis.BikerAnalysis;
import io.biker.management.user.analysis.SystemAnalysis;
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
@Tag(name = "Users/Management")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/users")
public class UserController {
    private BackOfficeService backOfficeService;
    private AnalysisService analysisService;

    public UserController(BackOfficeService backOfficeService, AnalysisService analysisService) {
        this.backOfficeService = backOfficeService;
        this.analysisService = analysisService;
    }

    @Operation(description = "GET endpoint for retrieving all back office users." +
            "\n\n Can only be done by back admins.", summary = "Get all back office users")
    @GetMapping("/backOffice")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public List<BackOfficeUser> getAllBackOfficeUsers() {
        return backOfficeService.getAllBackOfficeUsers();
    }

    @Operation(description = "GET endpoint for retrieving a single back office user given their id." +
            "\n\n Can only be done by back admins or the back office user being retrieved.", summary = "Get single back office user")
    @GetMapping("/backOffice/{id}")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "') or " +
            "(hasAuthority('" + Roles.BACK_OFFICE + "') and #id == authentication.principal.id)")
    public BackOfficeUser getSingleBackOfficeUser(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Back Office user ID") @PathVariable int id) {
        return backOfficeService.getSingleBackOfficeUser(id);
    }

    @Operation(description = "GET endpoint for retrieving an analysis of a biker's performance given their id." +
            "\n\n Can only be done by back office users.", summary = "Get an analysis of a single biker's performance")
    @GetMapping("/analysis/{id}")
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public BikerAnalysis AnalyzeBikerPerformance(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Biker ID") @PathVariable int id) {
        // FIXME: Implement some form of analysis
        BikerAnalysis analysis = new BikerAnalysis("Behold! Biker analysis");
        return analysis;
    }

    @Operation(description = "GET endpoint for retrieving an a report of the system's performance." +
            "\n\n Can only be done by admins.", summary = "Get a report of the system as a whole")
    @GetMapping("/analysis")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SystemAnalysis AnalyzeSystemPerformance() {
        // FIXME: Implement some form of analysis
        SystemAnalysis analysis = new SystemAnalysis("Behold! System analysis.");
        return analysis;
    }

    @Operation(description = "GET endpoint for generating suggestions for system improvements." +
            "\n\n Can only be done by back office users.", summary = "Get suggestions for improving system")
    @GetMapping("/improvements")
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public String GenerateSuggestions() {
        // FIXME: Implement some form of analysis
        return analysisService.generateReport();
    }
}
