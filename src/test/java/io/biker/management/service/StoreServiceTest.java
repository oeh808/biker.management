package io.biker.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.biker.management.store.entity.Store;
import io.biker.management.store.exception.StoreException;
import io.biker.management.store.exception.StoreExceptionMessages;
import io.biker.management.store.repo.StoreRepo;
import io.biker.management.store.service.StoreService;
import io.biker.management.store.service.StoreServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class StoreServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        StoreService service(StoreRepo repo) {
            return new StoreServiceImpl(repo);
        }
    }

    @MockBean
    private StoreRepo repo;

    @Autowired
    private StoreService service;

    private static Store store;

    @BeforeAll
    public static void setUp() {
        store = new Store(50, "Sorcerous Sundries", "SorcerousSundries@gmail.com", "+44 920350022",
                "password", null, null);
    }

    @Test
    public void createStore() {
        when(repo.save(store)).thenReturn(store);

        assertEquals(store, service.createStore(store));
    }

    @Test
    public void getAllStores() {
        List<Store> stores = new ArrayList<>();
        stores.add(store);
        when(repo.findAll()).thenReturn(stores);

        assertTrue(service.getAllStores().contains(store));
    }

    @Test
    public void getSingleStore_Existant() {
        when(repo.findById(store.getId())).thenReturn(Optional.of(store));

        assertEquals(store, service.getSingleStore(store.getId()));
    }

    @Test
    public void getSingleStore_NonExistant() {
        when(repo.findById(store.getId() - 1)).thenReturn(Optional.empty());

        StoreException ex = assertThrows(StoreException.class,
                () -> {
                    service.getSingleStore(store.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(StoreExceptionMessages.STORE_NOT_FOUND));
    }

    @Test
    public void deleteStore_Existant() {
        when(repo.findById(store.getId())).thenReturn(Optional.of(store));

        service.deleteStore(store.getId());

        verify(repo, times(1)).deleteById(store.getId());
    }

    @Test
    public void deleteStore_NonExistant() {
        when(repo.findById(store.getId() - 1)).thenReturn(Optional.empty());

        StoreException ex = assertThrows(StoreException.class,
                () -> {
                    service.deleteStore(store.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(StoreExceptionMessages.STORE_NOT_FOUND));
    }
}
