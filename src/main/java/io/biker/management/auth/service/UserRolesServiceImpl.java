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

@Service
public class UserRolesServiceImpl implements UserDetailsService, UserRolesService {
    @Autowired
    private UserRolesRepo repository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Optional<UserRoles> userDetail = repository.findByUser_Email(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new CustomAuthException(AuthExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD));
    }

    @Override
    public UserRoles addUser(UserRoles userRoles) {
        if (isDuplicatePhoneNumber(userRoles.getUser().getPhoneNumber())
                || isDuplicateUsername(userRoles.getUser().getEmail())) {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }

        return repository.save(userRoles);
    }

    @Override
    public void deleteUser(int id) {
        if (!userExists(id)) {
            throw new CustomAuthException(AuthExceptionMessages.USER_DOES_NOT_EXIST);
        }

        repository.deleteById(id);
    }

    @Override
    public boolean isDuplicateUsername(String username) {
        Optional<UserRoles> opUser = repository.findByUser_Email(username);

        return opUser.isPresent();
    }

    @Override
    public boolean isDuplicatePhoneNumber(String phoneNum) {
        Optional<UserRoles> opUser = repository.findByUser_PhoneNumber(phoneNum);

        return opUser.isPresent();
    }

    // Helper functions
    private boolean userExists(int id) {
        Optional<UserRoles> opUser = repository.findById(id);

        return opUser.isPresent();
    }
}
