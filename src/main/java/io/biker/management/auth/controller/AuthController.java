package io.biker.management.auth.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.dto.AuthRequestDTO;
import io.biker.management.auth.service.JwtService;
import io.biker.management.auth.service.UserRolesService;
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
@Tag(name = "Authorization", description = "Controller for handling mappings authentication and registering users")
@RequestMapping("/auth")
public class AuthController {

    private UserRolesService userRolesService;
    private JwtService jwtService;

    public AuthController(@Qualifier("userRolesServiceImpl") UserRolesService userRolesService, JwtService jwtService) {
        this.userRolesService = userRolesService;
        this.jwtService = jwtService;
    }

    @Operation(description = "POST endpoint for registering a customer and assigning their roles. (Entity must be created before registeration)"
            +
            "\n\n Can only be done by admins." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Register a customer")
    @PostMapping("/customers/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "')")
    public ResponseEntity<SuccessResponse> registerCustomer(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Customer ID") @PathVariable int id) {
        log.info("Recieved: POST request to /auth/customers/" + id);
        userRolesService.registerCustomer(id);

        return new ResponseEntity<SuccessResponse>(new SuccessResponse(Responses.USER_ADDED), HttpStatus.ACCEPTED);
    }

    @Operation(description = "POST endpoint for registering a biker and assigning their roles. (Entity must be created before registeration)"
            +
            "\n\n Can only be done by back office users." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Register a biker")
    @PostMapping("/bikers/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
    public ResponseEntity<SuccessResponse> registerBiker(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Biker ID") @PathVariable int id) {
        log.info("Recieved: POST request to /auth/bikers/" + id);
        userRolesService.registerBiker(id);

        return new ResponseEntity<SuccessResponse>(new SuccessResponse(Responses.USER_ADDED), HttpStatus.ACCEPTED);
    }

    @Operation(description = "POST endpoint for registering a back office user and assigning their roles. (Entity must be created before registeration)"
            +
            "\n\n Can only be done by back office users." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Register a back office user")
    @PostMapping("/backOffice/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
    public ResponseEntity<SuccessResponse> registerBackOfficeUser(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Biker ID") @PathVariable int id) {
        log.info("Recieved: POST request to /auth/backOffice/" + id);
        userRolesService.registerBackOfficeUser(id);

        return new ResponseEntity<SuccessResponse>(new SuccessResponse(Responses.USER_ADDED), HttpStatus.ACCEPTED);
    }

    @Operation(description = "POST endpoint for registering a store and assigning their roles. (Entity must be created before registeration)"
            +
            "\n\n Can only be done by admins." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Register a store")
    @PostMapping("/stores/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "')")
    public ResponseEntity<SuccessResponse> registerStore(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Store ID") @PathVariable int id) {
        log.info("Recieved: POST request to /auth/stores/" + id);
        userRolesService.registerStore(id);

        return new ResponseEntity<SuccessResponse>(new SuccessResponse(Responses.USER_ADDED), HttpStatus.ACCEPTED);
    }

    @Operation(description = "POST endpoint for generating a Jwt Token given a user name and password (Basically the login mapping)."
            +
            "\n\n Returns a Jwt token as an instance of String", summary = "Generate Jwt Token")
    @PostMapping("/generateToken")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of AuthRequestDTO")
    public String authenticateAndGetToken(@Valid @RequestBody AuthRequestDTO authRequest) {
        log.info("Recieved: POST request to /auth/generateToken");
        return jwtService.authenticateAndGetToken(authRequest.username(), authRequest.password());
    }

    @Operation(description = "DELETE endpoint for deleting a user and their roles (Does not affect the user's respective entity table)."
            +
            "\n\n Can only be done by admins." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Delete Store Info")
    @Transactional
    @DeleteMapping("/users/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "')")
    public ResponseEntity<SuccessResponse> deleteStore(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "User ID") @PathVariable int id) {
        log.info("Recieved: DELETE request to /auth/users/" + id);
        userRolesService.deleteUser(id);
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(Responses.USER_DELETED), HttpStatus.ACCEPTED);
    }

}
