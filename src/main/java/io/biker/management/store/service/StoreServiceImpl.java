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
        if (hasUniqueEmail(store.getEmail()) && hasUniquePhoneNumber(store.getPhoneNumber())) {
            store.setPassword(encoder.encode(store.getPassword()));

            return storeRepo.save(store);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Override
    public List<Store> getAllStores() {

        return storeRepo.findAll();
    }

    @Override
    public Store getSingleStore(int id) {
        Optional<Store> opStore = storeRepo.findById(id);
        if (opStore.isPresent()) {
            return opStore.get();
        } else {
            throw new StoreException(StoreExceptionMessages.STORE_NOT_FOUND);
        }
    }

    @Override
    public void deleteStore(int id) {
        getSingleStore(id);

        storeRepo.deleteById(id);
    }

    // Helper functions
    private boolean hasUniqueEmail(String email) {
        Optional<Store> opStore = storeRepo.findByEmail(email);
        return opStore.isEmpty();
    }

    private boolean hasUniquePhoneNumber(String phoneNumber) {
        Optional<Store> opStore = storeRepo.findByPhoneNumber(phoneNumber);
        return opStore.isEmpty();
    }
}
