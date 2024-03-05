package io.biker.management.backOffice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.backOffice.dto.BackOfficeUserCreationDTO;
import io.biker.management.backOffice.dto.BackOfficeUserReadingDTO;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.mapper.BackOfficeMapper;
import io.biker.management.backOffice.service.BackOfficeService;
import io.biker.management.constants.response.Responses;
import io.biker.management.errorHandling.responses.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Back Office Users")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/backOffice")
public class BaackOfficeController {
    private BackOfficeService backOfficeService;
    private BackOfficeMapper backOfficeMapper;

    public BaackOfficeController(BackOfficeService backOfficeService, BackOfficeMapper backOfficeMapper) {
        this.backOfficeService = backOfficeService;
        this.backOfficeMapper = backOfficeMapper;
    }

    @Operation(description = "POST endpoint for creating a back office user." +
            "\n\n Can only be done by back office users and admins.", summary = "Create a back office user")
    @PostMapping()
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of BackOfficeUserCreationDTO")
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public BackOfficeUserReadingDTO createBackOfficeUser(@Valid @RequestBody BackOfficeUserCreationDTO dto) {
        BackOfficeUser backOfficeUser = backOfficeService.createBackOfficeUser(backOfficeMapper.toBackOfficeUser(dto));

        return backOfficeMapper.toDto(backOfficeUser);
    }

    @Operation(description = "DELETE endpoint for deleting a back office user." +
            "\n\n Can only be done by admins.", summary = "Delete Back Office user")
    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse deleteBackOfficeUser(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Back Office user ID") @PathVariable int id) {
        backOfficeService.deleteBackOfficeUser(id);
        return new SuccessResponse(Responses.BACK_OFFICE_USER_DELETED);
    }
}
