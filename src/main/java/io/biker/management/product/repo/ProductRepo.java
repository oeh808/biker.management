package io.biker.management.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.product.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
