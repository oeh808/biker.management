package io.biker.management.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.dto.UserCreationDTO;
import io.biker.management.auth.entity.AuthRequest;
import io.biker.management.auth.entity.UserInfo;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.mapper.AuthMapper;
import io.biker.management.auth.service.JwtService;
import io.biker.management.auth.service.UserInfoService;
import io.biker.management.back_office.service.BackOfficeService;
import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.service.BikerService;
import io.biker.management.error_handling.responses.SuccessResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserInfoService userinfoService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    private BikerService bikerService;
    private BackOfficeService backOfficeService;

    private AuthMapper authMapper;

    @PostMapping("/bikers")
    public SuccessResponse createBiker(@RequestBody UserCreationDTO dto) {
        if (!userinfoService.isDuplicateUsername(dto.username())) {
            Biker biker = bikerService.createBiker(authMapper.toBiker(dto));
            UserInfo user = authMapper.toUser_Biker(dto);
            user.setId(biker.getId());

            return new SuccessResponse(userinfoService.addUser(user));
        } else {
            // Throw error
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_USERNAME);
        }
    }

    @PostMapping("/bikers/register")
    public SuccessResponse registerBiker() {
        // TODO: process POST request
        return null;
    }

    @PostMapping("/backOffice")
    public SuccessResponse createBackOfficeUser() {
        // TODO: process POST request
        return null;
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
