package io.biker.management.customer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.biker.management.customer.entity.Customer;
import java.util.Optional;

public interface CustomerRepo extends MongoRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
