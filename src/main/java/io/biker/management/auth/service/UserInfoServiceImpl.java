package io.biker.management.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.biker.management.auth.entity.UserInfo;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.repo.UserInfoRepo;

@Service
public class UserInfoServiceImpl implements UserDetailsService, UserInfoService {
    @Autowired
    private UserInfoRepo repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByUsername(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(AuthExceptionMessages.USER_DOES_NOT_EXIST));
    }

    @Override
    public UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        return repository.save(userInfo);
    }

    @Override
    public void deleteUser(int id) {
        checkUserExists(id);
        repository.deleteById(id);
    }

    @Override
    public boolean isDuplicateUsername(String username) {
        Optional<UserInfo> opUser = repository.findByUsername(username);

        return opUser.isPresent();
    }

    // Helper functions
    public void checkUserExists(int id) {
        Optional<UserInfo> opUser = repository.findById(id);

        if (opUser.isPresent()) {
            return;
        } else {
            throw new CustomAuthException(AuthExceptionMessages.USER_DOES_NOT_EXIST);
        }
    }
}
