package io.biker.management.customer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.customer.entity.Customer;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
