package io.biker.management.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.store.entity.Store;

public interface StoreRepo extends JpaRepository<Store, Integer> {

}
