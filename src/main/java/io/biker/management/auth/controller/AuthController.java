package io.biker.management.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.entity.AuthRequest;
import io.biker.management.auth.service.JwtService;
import io.biker.management.auth.service.UserInfoService;
import io.biker.management.error_handling.responses.SuccessResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserInfoService userinfoService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/admins")
    public SuccessResponse createAdmin() {
        // TODO: process POST request
        return null;
    }

    @PostMapping("/bikers")
    public SuccessResponse createBiker() {
        // TODO: process POST request
        return null;
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
