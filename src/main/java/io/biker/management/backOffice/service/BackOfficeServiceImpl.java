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
import lombok.extern.log4j.Log4j2;

@Log4j2
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
        log.info("Running createBackOfficeUser(" + backOfficeUser.toString() + ") in BackOfficeServiceImpl...");
        if (hasUniqueEmail(backOfficeUser.getEmail()) && hasUniquePhoneNumber(backOfficeUser.getPhoneNumber())) {
            log.info("Encoding password...");
            backOfficeUser.setPassword(encoder.encode(backOfficeUser.getPassword()));

            log.info("Saving back office user...");
            return backOfficeRepo.save(backOfficeUser);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Override
    public List<BackOfficeUser> getAllBackOfficeUsers() {
        log.info("Running getAllBackOfficeUsers() in BackOfficeServiceImpl...");
        return backOfficeRepo.findAll();
    }

    @Override
    public BackOfficeUser getSingleBackOfficeUser(int id) {
        log.info("Running getSingleBackOfficeUser(" + id + ") in BackOfficeServiceImpl...");
        Optional<BackOfficeUser> opBackOfficeUser = backOfficeRepo.findById(id);
        if (opBackOfficeUser.isPresent()) {
            return opBackOfficeUser.get();
        } else {
            log.error("Invalid back office user id: " + id + "!");
            throw new BackOfficeException(BackOfficeExceptionMessages.BACK_OFFICE_USER_NOT_FOUND);
        }
    }

    @Override
    public void deleteBackOfficeUser(int id) {
        log.info("Running getSingleBackOfficeUser(" + id + ") in BackOfficeServiceImpl...");
        getSingleBackOfficeUser(id);
        log.info("Deleting back office user...");
        backOfficeRepo.deleteById(id);
    }

    // Helper functions
    private boolean hasUniqueEmail(String email) {
        log.info("Checking that email: " + email + " does not exist in the back office users table...");
        Optional<BackOfficeUser> opBackOfficeUser = backOfficeRepo.findByEmail(email);
        if (opBackOfficeUser.isPresent()) {
            log.error("Duplicate email detected!");
        }
        return opBackOfficeUser.isEmpty();
    }

    private boolean hasUniquePhoneNumber(String phoneNumber) {
        log.info("Checking that phone number: " + phoneNumber + " does not exist in the back office users table...");
        Optional<BackOfficeUser> opBackOfficeUser = backOfficeRepo.findByPhoneNumber(phoneNumber);
        if (opBackOfficeUser.isPresent()) {
            log.error("Duplicate phone number detected!");
        }
        return opBackOfficeUser.isEmpty();
    }
}
