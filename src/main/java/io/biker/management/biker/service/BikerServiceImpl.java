package io.biker.management.biker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.exception.BikerException;
import io.biker.management.biker.exception.BikerExceptionMessages;
import io.biker.management.biker.repo.BikerRepo;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BikerServiceImpl implements BikerService {
    private BikerRepo bikerRepo;
    private PasswordEncoder encoder;

    public BikerServiceImpl(BikerRepo bikerRepo, PasswordEncoder encoder) {
        this.bikerRepo = bikerRepo;
        this.encoder = encoder;
    }

    @Override
    public Biker createBiker(Biker biker) {
        log.info("Running createBiker(" + biker.toString() + ") in BikerServiceImpl...");
        if (hasUniqueEmail(biker.getEmail()) && hasUniquePhoneNumber(biker.getPhoneNumber())) {
            log.info("Encoding password...");
            biker.setPassword(encoder.encode(biker.getPassword()));

            log.info("Saving biker...");
            return bikerRepo.save(biker);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Override
    public List<Biker> getAllBikers() {
        log.info("Running getAllBikers() in BikerServiceImpl...");
        return bikerRepo.findAll();
    }

    @Override
    public Biker getSingleBiker(int id) {
        log.info("Running getSingleBiker(" + id + ") in BikerServiceImpl...");
        Optional<Biker> opBiker = bikerRepo.findById(id);
        if (opBiker.isPresent()) {
            return opBiker.get();
        } else {
            log.error("Invalid biker id: " + id + "!");
            throw new BikerException(BikerExceptionMessages.BIKER_NOT_FOUND);
        }
    }

    @Override
    public void deleteBiker(int id) {
        log.info("Running deleteBiker(" + id + ") in BikerServiceImpl...");
        getSingleBiker(id);
        log.info("Deleting biker...");
        bikerRepo.deleteById(id);
    }

    // Helper functions
    private boolean hasUniqueEmail(String email) {
        log.info("Checking that email: " + email + " does not exist in the bikers table...");
        Optional<Biker> opBiker = bikerRepo.findByEmail(email);
        if (opBiker.isPresent()) {
            log.error("Duplicate email detected!");
        }
        return opBiker.isEmpty();
    }

    private boolean hasUniquePhoneNumber(String phoneNumber) {
        log.info("Checking that phone number: " + phoneNumber + " does not exist in the bikers table...");
        Optional<Biker> opBiker = bikerRepo.findByPhoneNumber(phoneNumber);
        if (opBiker.isPresent()) {
            log.error("Duplicate phone number detected!");
        }
        return opBiker.isEmpty();
    }

}
