package io.biker.management.store.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.store.entity.Store;

public interface StoreRepo extends JpaRepository<Store, Integer> {

    Optional<Store> findByEmail(String email);

    Optional<Store> findByPhoneNumber(String phoneNumber);
}
