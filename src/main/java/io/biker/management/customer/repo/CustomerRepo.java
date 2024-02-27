package io.biker.management.customer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.customer.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
