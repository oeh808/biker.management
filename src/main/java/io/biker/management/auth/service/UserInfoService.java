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
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserInfoRepo repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByUsernameIgnoreCase(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(AuthExceptionMessages.USER_DOES_NOT_EXIST));
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added";
    }

    public String deleteUser(int id) {
        String username = getUsername(id);
        repository.deleteByUsernameIgnoreCase(username);

        return "User Deleted";
    }

    // Helper functions
    public String getUsername(int id) {
        Optional<UserInfo> opUser = repository.findById(id);

        if (opUser.isPresent()) {
            return opUser.get().getUsername();
        } else {
            throw new CustomAuthException(AuthExceptionMessages.USER_DOES_NOT_EXIST);
        }
    }

    public boolean isDuplicateUsername(String username) {
        Optional<UserInfo> opUser = repository.findByUsernameIgnoreCase(username);

        return opUser.isPresent();
    }
}
