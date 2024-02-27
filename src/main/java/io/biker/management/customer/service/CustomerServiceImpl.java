package io.biker.management.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.biker.management.customer.entity.Customer;
import io.biker.management.user.Address;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public Customer createCustomer(Customer customer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCustomer'");
    }

    @Override
    public List<Customer> getAllCustomers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCustomers'");
    }

    @Override
    public Customer getSingleCustomer(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSingleCustomer'");
    }

    @Override
    public List<Address> setDeliveryAddresses(List<Address> addresses) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDeliveryAddresses'");
    }

    @Override
    public void deleteCustomer(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCustomer'");
    }

}
