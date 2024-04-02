package io.biker.management.store.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.biker.management.store.entity.Store;

public interface StoreRepo extends MongoRepository<Store, Integer> {

    Optional<Store> findByEmail(String email);

    Optional<Store> findByPhoneNumber(String phoneNumber);
}
