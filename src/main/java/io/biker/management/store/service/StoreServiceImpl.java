package io.biker.management.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.biker.management.store.entity.Store;
import io.biker.management.store.exception.StoreException;
import io.biker.management.store.exception.StoreExceptionMessages;
import io.biker.management.store.repo.StoreRepo;

@Service
public class StoreServiceImpl implements StoreService {
    private StoreRepo storeRepo;

    public StoreServiceImpl(StoreRepo storeRepo) {
        this.storeRepo = storeRepo;
    }

    @Override
    public Store createStore(Store store) {
        return storeRepo.save(store);
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
}
