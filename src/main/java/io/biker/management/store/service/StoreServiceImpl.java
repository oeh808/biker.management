package io.biker.management.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.store.entity.Store;
import io.biker.management.store.exception.StoreException;
import io.biker.management.store.exception.StoreExceptionMessages;
import io.biker.management.store.repo.StoreRepo;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class StoreServiceImpl implements StoreService {
    private StoreRepo storeRepo;
    private PasswordEncoder encoder;

    public StoreServiceImpl(StoreRepo storeRepo, PasswordEncoder encoder) {
        this.storeRepo = storeRepo;
        this.encoder = encoder;
    }

    @Override
    public Store createStore(Store store) {
        log.info("Running createStore(" + store.toString() + ") in StoreServiceImpl...");
        if (hasUniqueEmail(store.getEmail()) && hasUniquePhoneNumber(store.getPhoneNumber())) {
            log.info("Encoding password...");
            store.setPassword(encoder.encode(store.getPassword()));

            log.info("Saving store...");
            return storeRepo.save(store);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Override
    public List<Store> getAllStores() {
        log.info("Running getAllStores() in StoreServiceImpl...");

        return storeRepo.findAll();
    }

    @Override
    public Store getSingleStore(int id) {
        log.info("Running getSingleStore(" + id + ") in StoreServiceImpl...");

        Optional<Store> opStore = storeRepo.findById(id);
        if (opStore.isPresent()) {
            return opStore.get();
        } else {
            log.error("Invalid store id: " + id + "!");
            throw new StoreException(StoreExceptionMessages.STORE_NOT_FOUND);
        }
    }

    @Override
    public void deleteStore(int id) {
        log.info("Running deleteStore(" + id + ") in StoreServiceImpl...");

        getSingleStore(id);

        log.info("Deleting store...");
        storeRepo.deleteById(id);
    }

    // Helper functions
    private boolean hasUniqueEmail(String email) {
        log.info("Checking that email: " + email + " does not exist in the stores table...");
        Optional<Store> opStore = storeRepo.findByEmail(email);
        if (opStore.isPresent()) {
            log.error("Duplicate email detected!");
        }
        return opStore.isEmpty();
    }

    private boolean hasUniquePhoneNumber(String phoneNumber) {
        log.info("Checking that phone number: " + phoneNumber + " does not exist in the stores table...");
        Optional<Store> opStore = storeRepo.findByPhoneNumber(phoneNumber);
        if (opStore.isPresent()) {
            log.error("Duplicate phone number detected!");
        }
        return opStore.isEmpty();
    }
}
