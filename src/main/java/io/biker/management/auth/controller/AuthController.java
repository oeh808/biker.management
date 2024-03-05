package io.biker.management.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.auth.dto.AuthRequestDTO;
import io.biker.management.auth.entity.UserRoles;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.service.JwtService;
import io.biker.management.auth.service.UserRolesService;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.service.BackOfficeService;
import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.service.BikerService;
import io.biker.management.constants.response.Responses;
import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.service.CustomerService;
import io.biker.management.errorHandling.responses.SuccessResponse;
import io.biker.management.store.entity.Store;
import io.biker.management.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Authorization", description = "Controller for handling mappings authentication and registering users")
@RequestMapping("/auth")
public class AuthController {
    private UserRolesService userRolesService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    private CustomerService customerService;
    private BikerService bikerService;
    private BackOfficeService backOfficeService;
    private StoreService storeService;

    public AuthController(CustomerService customerService, UserRolesService userRolesService, JwtService jwtService,
            AuthenticationManager authenticationManager, BikerService bikerService,
            BackOfficeService backOfficeService, StoreService storeService) {
        this.userRolesService = userRolesService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;

        this.customerService = customerService;
        this.bikerService = bikerService;
        this.backOfficeService = backOfficeService;
        this.storeService = storeService;
    }

    @Operation(description = "POST endpoint for registering a customer and assigning their roles. (Entity must be created before registeration)"
            +
            "\n\n Can only be done by admins." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Register a customer")
    @PostMapping("/customers/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse registerCustomer(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Customer ID") @PathVariable int id) {
        Customer customer = customerService.getSingleCustomer(id);

        UserRoles user = new UserRoles(id, customer, Roles.CUSTOMER);
        userRolesService.addUser(user);

        return new SuccessResponse(Responses.USER_ADDED);
    }

    @Operation(description = "POST endpoint for registering a biker and assigning their roles. (Entity must be created before registeration)"
            +
            "\n\n Can only be done by back office users." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Register a biker")
    @PostMapping("/bikers/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public SuccessResponse registerBiker(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Biker ID") @PathVariable int id) {
        Biker biker = bikerService.getSingleBiker(id);

        UserRoles user = new UserRoles(id, biker, Roles.BIKER);
        userRolesService.addUser(user);

        return new SuccessResponse(Responses.USER_ADDED);
    }

    @Operation(description = "POST endpoint for registering a back office user and assigning their roles. (Entity must be created before registeration)"
            +
            "\n\n Can only be done by back office users." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Register a back office user")
    @PostMapping("/backOffice/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.BACK_OFFICE + "')")
    public SuccessResponse registerBackOfficeUser(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Biker ID") @PathVariable int id) {
        BackOfficeUser backOfficeUser = backOfficeService.getSingleBackOfficeUser(id);

        UserRoles user = new UserRoles(id, backOfficeUser, Roles.BACK_OFFICE);
        userRolesService.addUser(user);

        return new SuccessResponse(Responses.USER_ADDED);
    }

    @Operation(description = "POST endpoint for registering a store and assigning their roles. (Entity must be created before registeration)"
            +
            "\n\n Can only be done by admins." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Register a store")
    @PostMapping("/stores/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse registerStore(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Store ID") @PathVariable int id) {
        Store store = storeService.getSingleStore(id);

        UserRoles user = new UserRoles(id, store, Roles.STORE);
        userRolesService.addUser(user);

        return new SuccessResponse(Responses.USER_ADDED);
    }

    @Operation(description = "POST endpoint for generating a Jwt Token given a user name and password (Basically the login mapping)."
            +
            "\n\n Returns a Jwt token as an instance of String", summary = "Generate Jwt Token")
    @PostMapping("/generateToken")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of AuthRequestDTO")
    public String authenticateAndGetToken(@Valid @RequestBody AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        } else {
            /*
             * Ideally error should contain the same error message as other invalid
             * authentication for token generation to obscure details from potential
             * malicious user.
             */
            throw new UsernameNotFoundException(AuthExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD);
        }
    }

    @Operation(description = "DELETE endpoint for deleting a user and their roles (Does not affect the user's respective entity table)."
            +
            "\n\n Can only be done by admins." +
            "\n\n Returns a response as an instance of SuccessResponse.", summary = "Delete Store Info")
    @Transactional
    @DeleteMapping("/users/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse deleteStore(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "User ID") @PathVariable int id) {
        userRolesService.deleteUser(id);
        return new SuccessResponse(Responses.USER_DELETED);
    }

}
