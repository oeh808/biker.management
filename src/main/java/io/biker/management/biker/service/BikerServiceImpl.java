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
        if (hasUniqueEmail(biker.getEmail()) && hasUniquePhoneNumber(biker.getPhoneNumber())) {
            biker.setPassword(encoder.encode(biker.getPassword()));

            return bikerRepo.save(biker);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Override
    public List<Biker> getAllBikers() {
        return bikerRepo.findAll();
    }

    @Override
    public Biker getSingleBiker(int id) {
        Optional<Biker> opBiker = bikerRepo.findById(id);
        if (opBiker.isPresent()) {
            return opBiker.get();
        } else {
            throw new BikerException(BikerExceptionMessages.BIKER_NOT_FOUND);
        }
    }

    @Override
    public void deleteBiker(int id) {
        getSingleBiker(id);
        bikerRepo.deleteById(id);
    }

    // Helper functions
    private boolean hasUniqueEmail(String email) {
        Optional<Biker> opBiker = bikerRepo.findByEmail(email);
        return opBiker.isEmpty();
    }

    private boolean hasUniquePhoneNumber(String phoneNumber) {
        Optional<Biker> opBiker = bikerRepo.findByPhoneNumber(phoneNumber);
        return opBiker.isEmpty();
    }

}
