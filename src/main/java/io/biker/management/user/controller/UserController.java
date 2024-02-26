package io.biker.management.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.back_office.entity.BackOfficeUser;
import io.biker.management.back_office.service.BackOfficeService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/management")
public class UserController {
    private BackOfficeService backOfficeService;

    public UserController(BackOfficeService backOfficeService) {
        this.backOfficeService = backOfficeService;
    }

    @GetMapping("/backOffice")
    public List<BackOfficeUser> getAllBackOfficeUsers() {
        return backOfficeService.getAllBackOfficeUsers();
    }

    @GetMapping("/backOffice/{id}")
    public BackOfficeUser getSingleBackOfficeUser(@PathVariable int id) {
        return backOfficeService.getSingleBackOfficeUser(id);
    }

    @GetMapping("/analysis/{id}")
    public String AnalyzeBikerPerformance(@PathVariable int id) {
        // TODO: process GET request
        return null;
    }

    @GetMapping("/analysis")
    public String AnalyzeSystemPerformance() {
        // TODO: process GET request
        return null;
    }

    @GetMapping("/improvements")
    public String GenerateSuggestions() {
        // TODO: process GET request
        return null;
    }
}
