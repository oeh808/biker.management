package io.biker.management.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.exception.CustomerException;
import io.biker.management.customer.exception.CustomerExceptionMessages;
import io.biker.management.customer.repo.CustomerRepo;
import io.biker.management.user.Address;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Customer getSingleCustomer(int id) {
        Optional<Customer> opCustomer = customerRepo.findById(id);
        if (opCustomer.isPresent()) {
            return opCustomer.get();
        } else {
            throw new CustomerException(CustomerExceptionMessages.CUSTOMER_NOT_FOUND);
        }
    }

    @Override
    public Set<Address> addDeliveryAddress(int id, Address address) {
        Customer customer = getSingleCustomer(id);
        customer.addAddress(address);
        customerRepo.save(customer);

        return customer.getAddresses();
    }

    @Override
    public void deleteCustomer(int id) {
        getSingleCustomer(id);
        customerRepo.deleteById(id);
    }

}
