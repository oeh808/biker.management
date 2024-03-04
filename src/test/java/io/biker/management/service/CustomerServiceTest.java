package io.biker.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.exception.CustomerException;
import io.biker.management.customer.exception.CustomerExceptionMessages;
import io.biker.management.customer.repo.CustomerRepo;
import io.biker.management.customer.service.CustomerService;
import io.biker.management.customer.service.CustomerServiceImpl;
import io.biker.management.user.Address;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        CustomerService service(CustomerRepo repo) {
            return new CustomerServiceImpl(repo);
        }
    }

    @MockBean
    private CustomerRepo repo;

    @Autowired
    private CustomerService service;

    private static Customer customer;

    @BeforeAll
    public static void setUp() {
        customer = new Customer(50, "Volo", "BardMan", "+44 770820695", "password");
    }

    @BeforeEach
    public void setUpForEach() {
        customer.setAddresses(new HashSet<Address>());
    }

    @Test
    public void createCustomer() {
        when(repo.save(customer)).thenReturn(customer);

        assertEquals(customer, service.createCustomer(customer));
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        when(repo.findAll()).thenReturn(customers);

        assertTrue(service.getAllCustomers().contains(customer));
    }

    @Test
    public void getCustomer_Existant() {
        when(repo.findById(customer.getId())).thenReturn(Optional.of(customer));

        assertEquals(customer, service.getSingleCustomer(customer.getId()));
    }

    @Test
    public void getCustomer_NonExistant() {
        when(repo.findById(customer.getId() - 1)).thenReturn(Optional.empty());

        CustomerException ex = assertThrows(CustomerException.class, () -> {
            service.getSingleCustomer(customer.getId() - 1);
        });
        assertTrue(ex.getMessage().contains(CustomerExceptionMessages.CUSTOMER_NOT_FOUND));
    }

    @Test
    public void addAddress_CustomerExistant() {
        when(repo.findById(customer.getId())).thenReturn(Optional.of(customer));
        Address address = new Address("Basilisk Gate", "Baldur's Gate", "N/A", "B73 G22", "Faerun");

        service.addDeliveryAddress(customer.getId(), address);

        verify(repo, times(1)).save(any(Customer.class));

        assertTrue(customer.getAddresses().contains(address));
    }

    @Test
    public void addAddress_CustomerNonExistant() {
        when(repo.findById(customer.getId() - 1)).thenReturn(Optional.empty());

        CustomerException ex = assertThrows(CustomerException.class, () -> {
            service.addDeliveryAddress(customer.getId() - 1, null);
        });
        assertTrue(ex.getMessage().contains(CustomerExceptionMessages.CUSTOMER_NOT_FOUND));

        assertTrue(customer.getAddresses().isEmpty());
    }

    @Test
    public void deleteCustomer_Existant() {
        when(repo.findById(customer.getId())).thenReturn(Optional.of(customer));

        service.deleteCustomer(customer.getId());

        verify(repo, times(1)).deleteById(customer.getId());
    }

    @Test
    public void deleteCustomer_NonExistant() {
        when(repo.findById(customer.getId() - 1)).thenReturn(Optional.empty());

        CustomerException ex = assertThrows(CustomerException.class, () -> {
            service.deleteCustomer(customer.getId() - 1);
        });
        assertTrue(ex.getMessage().contains(CustomerExceptionMessages.CUSTOMER_NOT_FOUND));

        verify(repo, times(0)).deleteById(customer.getId() - 1);
    }
}
