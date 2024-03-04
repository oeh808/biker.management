package io.biker.management.backOffice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.backOffice.service.BackOfficeService;
import io.biker.management.constants.response.Responses;
import io.biker.management.errorHandling.responses.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Back Office Users")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/backOffice")
public class backOfficeController {
    private BackOfficeService backOfficeService;

    @Operation(description = "DELETE endpoint for deleting a back office user." +
            "\n\n Can only be done by admins.", summary = "Delete Back Office user")
    @Transactional
    @DeleteMapping("/backOffice/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse deleteBackOfficeUser(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Back Office user ID") @PathVariable int id) {
        backOfficeService.deleteBackOfficeUser(id);
        return new SuccessResponse(Responses.BACK_OFFICE_USER_DELETED);
    }
}
