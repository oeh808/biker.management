package io.biker.management.product.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.biker.management.product.entity.Product;
import java.util.List;
import io.biker.management.store.entity.Store;


public interface ProductRepo extends MongoRepository<Product, Integer> {
    public Optional<Product> findByProductIdAndStore(int productId, Store store);

    public List<Product> findByStore(Store store);
}
