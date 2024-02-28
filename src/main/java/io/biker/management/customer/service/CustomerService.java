package io.biker.management.customer.service;

import java.util.List;
import java.util.Set;

import io.biker.management.customer.entity.Customer;
import io.biker.management.user.Address;

public interface CustomerService {
    public Customer createCustomer(Customer customer);

    public List<Customer> getAllCustomers();

    public Customer getSingleCustomer(int id);

    public Set<Address> addDeliveryAddress(int id, Address address);

    public void deleteCustomer(int id);
}
