package io.biker.management.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.auth.dto.AuthRequestDTO;
import io.biker.management.auth.dto.UserCreationDTO;
import io.biker.management.auth.entity.UserInfo;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.mapper.AuthMapper;
import io.biker.management.auth.service.JwtService;
import io.biker.management.auth.service.UserInfoService;
import io.biker.management.back_office.entity.BackOfficeUser;
import io.biker.management.back_office.service.BackOfficeService;
import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.service.BikerService;
import io.biker.management.error_handling.responses.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Authorization")
@RequestMapping("/auth")
public class AuthController {
    private UserInfoService userinfoService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    private BikerService bikerService;
    private BackOfficeService backOfficeService;

    private AuthMapper authMapper;

    public AuthController(UserInfoService userinfoService, JwtService jwtService,
            AuthenticationManager authenticationManager, BikerService bikerService, BackOfficeService backOfficeService,
            AuthMapper authMapper) {
        this.userinfoService = userinfoService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.bikerService = bikerService;
        this.backOfficeService = backOfficeService;
        this.authMapper = authMapper;
    }

    @Operation(description = "POST endpoint for creating a bikers.", summary = "Create a biker")
    @PostMapping("/bikers")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of UserCreationDTO")
    public SuccessResponse createBiker(@Valid @RequestBody UserCreationDTO dto) {
        if (!userinfoService.isDuplicateUsername(dto.username())) {
            Biker biker = bikerService.createBiker(authMapper.toBiker(dto));
            UserInfo user = authMapper.toUser_Biker(dto);
            user.setId(biker.getId());

            return new SuccessResponse(userinfoService.addUser(user));
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_USERNAME);
        }
    }

    @Operation(description = "POST endpoint for creating a back office user." +
            "\n\n Can only be done by admins.", summary = "Create a back office user")
    @PostMapping("/backOffice")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of UserCreationDTO")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public SuccessResponse createBackOfficeUser(@Valid @RequestBody UserCreationDTO dto) {
        if (!userinfoService.isDuplicateUsername(dto.username())) {
            BackOfficeUser boUser = backOfficeService.createBackOfficeUser(authMapper.toBoUser(dto));
            UserInfo user = authMapper.toUser_BoUser(dto);
            user.setId(boUser.getId());

            return new SuccessResponse(userinfoService.addUser(user));
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_USERNAME);
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
}
