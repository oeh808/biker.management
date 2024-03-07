package io.biker.management.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.biker.management.auth.entity.UserRoles;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.repo.UserRolesRepo;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserRolesServiceImpl implements UserDetailsService, UserRolesService {
    @Autowired
    private UserRolesRepo repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Running loadUserByUsername(" + username + ") in UserRolesServiceImpl...");
        Optional<UserRoles> userDetail = repository.findByUser_Email(username);

        // Converting userDetail to UserDetails
        log.info("Attempting to retrieve user details by username...");
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new CustomAuthException(AuthExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD));
    }

    @Override
    public UserRoles addUser(UserRoles userRoles) {
        log.info("Running addUser(" + userRoles + ") in UserRolesServiceImpl...");
        if (isDuplicatePhoneNumber(userRoles.getUser().getPhoneNumber())
                || isDuplicateUsername(userRoles.getUser().getEmail())) {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }

        log.info("Saving user registered with roles...");
        return repository.save(userRoles);
    }

    @Override
    public void deleteUser(int id) {
        log.info("Running deleteUser(" + id + ") in UserRolesServiceImpl...");
        if (!userExists(id)) {
            throw new CustomAuthException(AuthExceptionMessages.USER_DOES_NOT_EXIST);
        }

        log.info("Deleting user registered with roles...");
        repository.deleteById(id);
    }

    // Helper functions
    private boolean isDuplicateUsername(String username) {
        log.info("Checking that email: " + username + " does not exist in the user roles table...");
        Optional<UserRoles> opUser = repository.findByUser_Email(username);
        if (opUser.isPresent()) {
            log.error("Duplicate email detected!");
        }

        return opUser.isPresent();
    }

    private boolean isDuplicatePhoneNumber(String phoneNum) {
        log.info("Checking that phone number: " + phoneNum + " does not exist in the user roles table...");
        Optional<UserRoles> opUser = repository.findByUser_PhoneNumber(phoneNum);
        if (opUser.isPresent()) {
            log.error("Duplicate phone number detected!");
        }

        return opUser.isPresent();
    }

    private boolean userExists(int id) {
        log.info("Checking that user with id: " + id + " exists...");
        Optional<UserRoles> opUser = repository.findById(id);

        if (opUser.isEmpty()) {
            log.error("Invalid user id: " + id + "!");
        }
        return opUser.isPresent();
    }
}
