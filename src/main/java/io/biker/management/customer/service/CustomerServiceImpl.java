package io.biker.management.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.exception.CustomerException;
import io.biker.management.customer.exception.CustomerExceptionMessages;
import io.biker.management.customer.repo.CustomerRepo;
import io.biker.management.user.Address;

@Service
public class CustomerServiceImpl implements CustomerService {

    private PasswordEncoder encoder;
    private CustomerRepo customerRepo;

    public CustomerServiceImpl(PasswordEncoder encoder, CustomerRepo customerRepo) {
        this.encoder = encoder;
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (hasUniqueEmail(customer.getEmail()) && hasUniquePhoneNumber(customer.getPhoneNumber())) {
            customer.setPassword(encoder.encode(customer.getPassword()));

            return customerRepo.save(customer);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
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

    // Helper functions
    private boolean hasUniqueEmail(String email) {
        Optional<Customer> opCustomer = customerRepo.findByEmail(email);
        return opCustomer.isEmpty();
    }

    private boolean hasUniquePhoneNumber(String phoneNumber) {
        Optional<Customer> opCustomer = customerRepo.findByPhoneNumber(phoneNumber);
        return opCustomer.isEmpty();
    }

}
