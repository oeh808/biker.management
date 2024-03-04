package io.biker.management.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.repo.CustomerRepo;

@ActiveProfiles("test")
@DataJpaTest
public class CustomerRepoTest {
    @Autowired
    private CustomerRepo repo;

    private static Customer customer;

    @BeforeAll
    public static void setUp() {
        customer = new Customer(50, "Volo", "BardMan", "+44 770820695", "password");
    }

    @BeforeEach
    public void setUpForEach() {
        customer = repo.save(customer);
    }

    @AfterEach
    public void tearDownForEach() {
        repo.deleteAll();
    }

    @Test
    public void findByEmail_Existant() {
        Optional<Customer> opCustomer = repo.findByEmail(customer.getEmail());

        assertTrue(opCustomer.isPresent());
        assertEquals(customer, opCustomer.get());
    }

    @Test
    public void findByEmail_NonExistant() {
        Optional<Customer> opCustomer = repo.findByEmail("Blah");

        assertTrue(opCustomer.isEmpty());
    }

    @Test
    public void findByPhoneNumber_Existant() {
        Optional<Customer> opCustomer = repo.findByPhoneNumber(customer.getPhoneNumber());

        assertTrue(opCustomer.isPresent());
        assertEquals(customer, opCustomer.get());
    }

    @Test
    public void findByPhoneNumber_NonExistant() {
        Optional<Customer> opCustomer = repo.findByPhoneNumber("Blah");

        assertTrue(opCustomer.isEmpty());
    }
}
