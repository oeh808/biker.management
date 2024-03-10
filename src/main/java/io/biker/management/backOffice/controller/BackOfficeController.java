package io.biker.management.backOffice.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.backOffice.dto.BackOfficeUserCreationDTO;
import io.biker.management.backOffice.dto.BackOfficeUserReadingDTO;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.mapper.BackOfficeMapper;
import io.biker.management.backOffice.service.BackOfficeService;
import io.biker.management.constants.Roles_Const;
import io.biker.management.constants.response.Responses;
import io.biker.management.errorHandling.responses.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@Tag(name = "Back Office Users", description = "Controller for handling mappings for back office users")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/backOffice")
public class BackOfficeController {
    private BackOfficeService backOfficeService;
    private BackOfficeMapper backOfficeMapper;

    public BackOfficeController(BackOfficeService backOfficeService, BackOfficeMapper backOfficeMapper) {
        this.backOfficeService = backOfficeService;
        this.backOfficeMapper = backOfficeMapper;
    }

    @Operation(description = "POST endpoint for creating a back office user." +
            "\n\n Can only be done by back office users." +
            "\n\n Returns the customer created as an instance of BackOfficeUserReadingDTO.", summary = "Create a back office user")
    @PostMapping()
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of BackOfficeUserCreationDTO")
    @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
    public BackOfficeUserReadingDTO createBackOfficeUser(@Valid @RequestBody BackOfficeUserCreationDTO dto) {
        log.info("Recieved: POST request to /backOffice");
        BackOfficeUser backOfficeUser = backOfficeService.createBackOfficeUser(backOfficeMapper.toBackOfficeUser(dto));

        return backOfficeMapper.toDto(backOfficeUser);
    }

    @Operation(description = "GET endpoint for retrieving all back office users." +
            "\n\n Can only be done by back office users." +
            "\n\n Returns all back office users as a List of BackOfficeUser.", summary = "Get all back office users")
    @GetMapping()
    @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
    public List<BackOfficeUser> getAllBackOfficeUsers() {
        log.info("Recieved: GET request to /backOffice");
        return backOfficeService.getAllBackOfficeUsers();
    }

    @Operation(description = "GET endpoint for retrieving a single back office user given their id." +
            "\n\n Can only be done by back office users." +
            "\n\n Returns the back office user as an instance of BackOfficeUser.", summary = "Get single back office user")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
    public BackOfficeUser getSingleBackOfficeUser(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Back Office user ID") @PathVariable int id) {
        log.info("Recieved: GET request to /backOffice/" + id);
        return backOfficeService.getSingleBackOfficeUser(id);
    }

    @Operation(description = "DELETE endpoint for deleting a back office user." +
            "\n\n Can only be done by admins." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Delete a Back Office user")
    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "')")
    public SuccessResponse deleteBackOfficeUser(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Back Office user ID") @PathVariable int id) {
        log.info("Recieved: DELETE request to /backOffice/" + id);
        backOfficeService.deleteBackOfficeUser(id);
        return new SuccessResponse(Responses.BACK_OFFICE_USER_DELETED);
    }
}
