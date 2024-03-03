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
import io.biker.management.auth.dto.StoreCreationDTO;
import io.biker.management.auth.dto.UserCreationDTO;
import io.biker.management.auth.entity.UserInfo;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.mapper.AuthMapper;
import io.biker.management.auth.response.Responses;
import io.biker.management.auth.service.JwtService;
import io.biker.management.auth.service.UserInfoServiceImpl;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.service.BackOfficeService;
import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.service.BikerService;
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
@Tag(name = "Authorization")
@RequestMapping("/auth")
public class AuthController {
    private UserInfoServiceImpl userinfoService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    private CustomerService customerService;
    private BikerService bikerService;
    private BackOfficeService backOfficeService;
    private StoreService storeService;

    private AuthMapper authMapper;

    public AuthController(CustomerService customerService, UserInfoServiceImpl userinfoService, JwtService jwtService,
            AuthenticationManager authenticationManager, BikerService bikerService,
            BackOfficeService backOfficeService, StoreService storeService,
            AuthMapper authMapper) {
        this.userinfoService = userinfoService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;

        this.customerService = customerService;
        this.bikerService = bikerService;
        this.backOfficeService = backOfficeService;
        this.storeService = storeService;

        this.authMapper = authMapper;
    }

    @Operation(description = "POST endpoint for creating a customers.", summary = "Create a customer")
    @PostMapping("/customers")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of UserCreationDTO")
    public SuccessResponse createCustomer(@Valid @RequestBody UserCreationDTO dto) {
        if (!userinfoService.isDuplicateUsername(dto.username())
                && !userinfoService.isDuplicatePhoneNumber(dto.phoneNum())) {
            Customer customer = customerService.createCustomer(authMapper.toCustomer(dto));
            UserInfo user = authMapper.toUser_Customer(dto);
            user.setId(customer.getId());
            userinfoService.addUser(user);

            return new SuccessResponse(Responses.USER_ADDED);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Operation(description = "POST endpoint for creating a bikers.", summary = "Create a biker")
    @PostMapping("/bikers")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of UserCreationDTO")
    public SuccessResponse createBiker(@Valid @RequestBody UserCreationDTO dto) {
        if (!userinfoService.isDuplicateUsername(dto.username())
                && !userinfoService.isDuplicatePhoneNumber(dto.phoneNum())) {
            Biker biker = bikerService.createBiker(authMapper.toBiker(dto));
            UserInfo user = authMapper.toUser_Biker(dto);
            user.setId(biker.getId());
            userinfoService.addUser(user);

            return new SuccessResponse(Responses.USER_ADDED);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Operation(description = "POST endpoint for creating a back office user." +
            "\n\n Can only be done by admins.", summary = "Create a back office user")
    @PostMapping("/backOffice")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of UserCreationDTO")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse createBackOfficeUser(@Valid @RequestBody UserCreationDTO dto) {
        if (!userinfoService.isDuplicateUsername(dto.username())
                && !userinfoService.isDuplicatePhoneNumber(dto.phoneNum())) {
            BackOfficeUser boUser = backOfficeService.createBackOfficeUser(authMapper.toBoUser(dto));
            UserInfo user = authMapper.toUser_BoUser(dto);
            user.setId(boUser.getId());
            userinfoService.addUser(user);

            return new SuccessResponse(Responses.USER_ADDED);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Operation(description = "POST endpoint for registering a store.", summary = "Register a store")
    @PostMapping("/stores")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of StoreCreationDTO")
    public SuccessResponse registerStore(@Valid @RequestBody StoreCreationDTO dto) {
        if (!userinfoService.isDuplicateUsername(dto.name())
                && !userinfoService.isDuplicatePhoneNumber(dto.phoneNum())) {
            Store store = storeService.createStore(authMapper.toStore(dto));
            UserInfo user = authMapper.toUser_Store(dto);
            user.setId(store.getStoreId());
            userinfoService.addUser(user);

            return new SuccessResponse(Responses.USER_ADDED);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Operation(description = "POST endpoint for generating a Jwt Token given a user name and password.", summary = "Generate Jwt Token")
    @PostMapping("/generateToken")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of AuthRequestDTO")
    public String authenticateAndGetToken(@Valid @RequestBody AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        } else {
            /*
             * FIXME:
             * Ideally error should contain the same error message as other invalid
             * authentication for token generation to obscure details from potential
             * malicious user.
             */
            throw new UsernameNotFoundException(AuthExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD);
        }
    }

    @Operation(description = "DELETE endpoint for deleting a customer." +
            "\n\n Can only be done by admins.", summary = "Delete Customer")
    @Transactional
    @DeleteMapping("/customers/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse deleteCustomer(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Customer ID") @PathVariable int id) {
        customerService.deleteCustomer(id);
        userinfoService.deleteUser(id);
        return new SuccessResponse(Responses.USER_DELETED);
    }

    @Operation(description = "DELETE endpoint for deleting a biker." +
            "\n\n Can only be done by admins.", summary = "Delete Biker")
    @Transactional
    @DeleteMapping("/bikers/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse deleteBiker(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Biker ID") @PathVariable int id) {
        bikerService.deleteBiker(id);
        userinfoService.deleteUser(id);
        return new SuccessResponse(Responses.USER_DELETED);
    }

    @Operation(description = "DELETE endpoint for deleting a back office user." +
            "\n\n Can only be done by admins.", summary = "Delete Back Office user")
    @Transactional
    @DeleteMapping("/backOffice/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse deleteBackOfficeUser(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Back Office user ID") @PathVariable int id) {
        backOfficeService.deleteBackOfficeUser(id);
        userinfoService.deleteUser(id);
        return new SuccessResponse(Responses.USER_DELETED);
    }

    @Operation(description = "DELETE endpoint for deleting a store." +
            "\n\n Can only be done by admins.", summary = "Delete Store")
    @Transactional
    @DeleteMapping("/stores/{id}")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse deleteStore(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Store ID") @PathVariable int id) {
        storeService.deleteStore(id);
        userinfoService.deleteUser(id);
        return new SuccessResponse(Responses.USER_DELETED);
    }

}
