package io.biker.management.customer.service;

import io.biker.management.customer.entity.Customer;

public interface CustomerService {
    public Customer createCustomer(Customer customer);

    public void deleteCustomer(int id);
}
