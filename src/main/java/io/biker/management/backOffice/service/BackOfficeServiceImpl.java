package io.biker.management.backOffice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.exception.BackOfficeException;
import io.biker.management.backOffice.exception.BackOfficeExceptionMessages;
import io.biker.management.backOffice.repo.BackOfficeUserRepo;

@Service
public class BackOfficeServiceImpl implements BackOfficeService {
    private BackOfficeUserRepo backOfficeRepo;
    private PasswordEncoder encoder;

    public BackOfficeServiceImpl(BackOfficeUserRepo backOfficeRepo, PasswordEncoder encoder) {
        this.backOfficeRepo = backOfficeRepo;
        this.encoder = encoder;
    }

    @Override
    public BackOfficeUser createBackOfficeUser(BackOfficeUser backOfficeUser) {
        if (hasUniqueEmail(backOfficeUser.getEmail()) && hasUniquePhoneNumber(backOfficeUser.getPhoneNumber())) {
            backOfficeUser.setPassword(encoder.encode(backOfficeUser.getPassword()));

            return backOfficeRepo.save(backOfficeUser);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Override
    public List<BackOfficeUser> getAllBackOfficeUsers() {
        return backOfficeRepo.findAll();
    }

    @Override
    public BackOfficeUser getSingleBackOfficeUser(int id) {
        Optional<BackOfficeUser> opBackOfficeUser = backOfficeRepo.findById(id);
        if (opBackOfficeUser.isPresent()) {
            return opBackOfficeUser.get();
        } else {
            throw new BackOfficeException(BackOfficeExceptionMessages.BACK_OFFICE_USER_NOT_FOUND);
        }
    }

    @Override
    public void deleteBackOfficeUser(int id) {
        getSingleBackOfficeUser(id);
        backOfficeRepo.deleteById(id);
    }

    // Helper functions
    private boolean hasUniqueEmail(String email) {
        Optional<BackOfficeUser> opBackOfficeUser = backOfficeRepo.findByEmail(email);
        return opBackOfficeUser.isEmpty();
    }

    private boolean hasUniquePhoneNumber(String phoneNumber) {
        Optional<BackOfficeUser> opBackOfficeUser = backOfficeRepo.findByPhoneNumber(phoneNumber);
        return opBackOfficeUser.isEmpty();
    }
}
