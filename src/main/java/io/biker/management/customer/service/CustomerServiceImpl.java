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
import lombok.extern.log4j.Log4j2;

@Log4j2
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
        log.info("Running createCustomer(" + customer.toString() + ") in CustomerServiceImpl...");
        if (hasUniqueEmail(customer.getEmail()) && hasUniquePhoneNumber(customer.getPhoneNumber())) {
            log.info("Encoding password...");
            customer.setPassword(encoder.encode(customer.getPassword()));

            log.info("Saving customer...");
            return customerRepo.save(customer);
        } else {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        log.info("Running getAllCustomers() in CustomerServiceImpl...");
        return customerRepo.findAll();
    }

    @Override
    public Customer getSingleCustomer(int id) {
        log.info("Running getSingleCustomer(" + id + ") in CustomerServiceImpl...");
        Optional<Customer> opCustomer = customerRepo.findById(id);
        if (opCustomer.isPresent()) {
            return opCustomer.get();
        } else {
            log.error("Invalid customer id: " + id + "!");
            throw new CustomerException(CustomerExceptionMessages.CUSTOMER_NOT_FOUND);
        }
    }

    @Override
    public Set<Address> addDeliveryAddress(int id, Address address) {
        log.info("Running addDeliveryAddress(" + id + "," + address.toString() + ") in CustomerServiceImpl...");
        Customer customer = getSingleCustomer(id);
        customer.addAddress(address);
        log.info("Saving customer with added address...");
        customerRepo.save(customer);

        return customer.getAddresses();
    }

    @Override
    public void deleteCustomer(int id) {
        log.info("Running deleteCustomer(" + id + ") in CustomerServiceImpl...");
        getSingleCustomer(id);
        log.info("Deleting customer...");
        customerRepo.deleteById(id);
    }

    // Helper functions
    private boolean hasUniqueEmail(String email) {
        log.info("Checking that email: " + email + " does not exist in the customers table...");
        Optional<Customer> opCustomer = customerRepo.findByEmail(email);
        if (opCustomer.isPresent()) {
            log.error("Duplicate email detected!");
        }
        return opCustomer.isEmpty();
    }

    private boolean hasUniquePhoneNumber(String phoneNumber) {
        log.info("Checking that phone number: " + phoneNumber + " does not exist in the customers table...");
        Optional<Customer> opCustomer = customerRepo.findByPhoneNumber(phoneNumber);
        if (opCustomer.isPresent()) {
            log.error("Duplicate phone number detected!");
        }
        return opCustomer.isEmpty();
    }

}
