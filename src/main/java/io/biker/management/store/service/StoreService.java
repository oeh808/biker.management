package io.biker.management.store.service;

import java.util.List;

import io.biker.management.store.entity.Store;

public interface StoreService {
    public Store createStore(Store store);

    public List<Store> getAllStores();

    public Store getSingleStore(int id);

    public void deleteStore(int id);
}
