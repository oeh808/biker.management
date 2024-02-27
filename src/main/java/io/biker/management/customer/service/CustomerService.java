package io.biker.management.customer.service;

import java.util.List;

import io.biker.management.customer.entity.Customer;
import io.biker.management.user.Address;

//TODO: Implement services
public interface CustomerService {
    public Customer createCustomer(Customer customer);

    public List<Customer> getAllCustomers();

    public Customer getSingleCustomer(int id);

    public List<Address> setDeliveryAddresses(List<Address> addresses);

    public void deleteCustomer(int id);
}
