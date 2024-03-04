package io.biker.management.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.biker.management.auth.entity.UserInfo;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.repo.UserInfoRepo;

@Service
public class UserInfoServiceImpl implements UserDetailsService, UserInfoService {
    @Autowired
    private UserInfoRepo repository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Optional<UserInfo> userDetail = repository.findByUser_Email(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new CustomAuthException(AuthExceptionMessages.USER_DOES_NOT_EXIST));
    }

    @Override
    public UserInfo addUser(UserInfo userInfo) {
        if (isDuplicatePhoneNumber(userInfo.getUser().getPhoneNumber())
                || isDuplicateUsername(userInfo.getUser().getEmail())) {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }

        return repository.save(userInfo);
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
        Optional<UserInfo> opUser = repository.findByUser_Email(username);

        return opUser.isPresent();
    }

    @Override
    public boolean isDuplicatePhoneNumber(String phoneNum) {
        Optional<UserInfo> opUser = repository.findByUser_PhoneNumber(phoneNum);

        return opUser.isPresent();
    }

    // Helper functions
    private boolean userExists(int id) {
        Optional<UserInfo> opUser = repository.findById(id);

        return opUser.isPresent();
    }
}
