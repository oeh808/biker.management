package io.biker.management.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.back_office.entity.BackOfficeUser;
import io.biker.management.back_office.service.BackOfficeService;
import io.biker.management.user.analysis.AnalysisService;
import io.biker.management.user.analysis.BikerAnalysis;
import io.biker.management.user.analysis.SystemAnalysis;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
public class UserController {
    private BackOfficeService backOfficeService;
    private AnalysisService analysisService;

    public UserController(BackOfficeService backOfficeService, AnalysisService analysisService) {
        this.backOfficeService = backOfficeService;
        this.analysisService = analysisService;
    }

    @GetMapping("/backOffice")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public List<BackOfficeUser> getAllBackOfficeUsers() {
        return backOfficeService.getAllBackOfficeUsers();
    }

    @GetMapping("/backOffice/{id}")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public BackOfficeUser getSingleBackOfficeUser(@PathVariable int id) {
        return backOfficeService.getSingleBackOfficeUser(id);
    }

    @GetMapping("/analysis/{id}")
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public BikerAnalysis AnalyzeBikerPerformance(@PathVariable int id) {
        // FIXME: Implement some form of analysis
        BikerAnalysis analysis = new BikerAnalysis("Behold! Biker analysis");
        return analysis;
    }

    @GetMapping("/analysis")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SystemAnalysis AnalyzeSystemPerformance() {
        // FIXME: Implement some form of analysis
        SystemAnalysis analysis = new SystemAnalysis("Behold! System analysis.");
        return analysis;
    }

    @GetMapping("/improvements")
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public String GenerateSuggestions() {
        // FIXME: Implement some form of analysis
        return analysisService.generateReport();
    }
}
