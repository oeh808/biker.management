package io.biker.management.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.biker.management.auth.entity.UserRoles;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.repo.UserRolesRepo;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRolesRepo repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        log.info("Running loadUserByUsername(" + username + ") in UserRolesServiceImpl...");
        Optional<UserRoles> userDetail = repository.findByUser_Email(username);

        // Converting userDetail to UserDetails
        log.info("Attempting to retrieve user details by username...");
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new CustomAuthException(AuthExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD));
    }

}
