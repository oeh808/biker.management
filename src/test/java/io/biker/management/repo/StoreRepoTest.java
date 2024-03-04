package io.biker.management.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import io.biker.management.product.entity.Product;
import io.biker.management.store.entity.Store;
import io.biker.management.store.repo.StoreRepo;

@ActiveProfiles("test")
@DataJpaTest
public class StoreRepoTest {
    @Autowired
    private StoreRepo repo;

    private static Store store;

    @BeforeAll
    public static void setUp() {
        store = new Store(50, "Sorcerous Sundries", "SorcerousSundries@gmail.com", "+44 920350022",
                "password", null, new ArrayList<Product>());
    }

    @BeforeEach
    public void setUpForEach() {
        store = repo.save(store);
    }

    @AfterEach
    public void tearDownForEach() {
        repo.deleteAll();
    }

    @Test
    public void findByEmail_Existant() {
        Optional<Store> opStore = repo.findByEmail(store.getEmail());

        assertTrue(opStore.isPresent());
        assertEquals(store, opStore.get());
    }

    @Test
    public void findByEmail_NonExistant() {
        Optional<Store> opStore = repo.findByEmail("Blah");

        assertTrue(opStore.isEmpty());
    }

    @Test
    public void findByPhoneNumber_Existant() {
        Optional<Store> opStore = repo.findByPhoneNumber(store.getPhoneNumber());

        assertTrue(opStore.isPresent());
        assertEquals(store, opStore.get());
    }

    @Test
    public void findByPhoneNumber_NonExistant() {
        Optional<Store> opStore = repo.findByPhoneNumber("Blah");

        assertTrue(opStore.isEmpty());
    }
}
