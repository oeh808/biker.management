package io.biker.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import io.biker.management.product.entity.Product;
import io.biker.management.product.exception.ProductException;
import io.biker.management.product.exception.ProductExceptionMessages;
import io.biker.management.product.repo.ProductRepo;
import io.biker.management.product.service.ProductService;
import io.biker.management.product.service.ProductServiceImpl;
import io.biker.management.store.entity.Store;
import io.biker.management.store.exception.StoreException;
import io.biker.management.store.exception.StoreExceptionMessages;
import io.biker.management.store.repo.StoreRepo;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        ProductService service(ProductRepo productRepo, StoreRepo storeRepo) {
            return new ProductServiceImpl(productRepo, storeRepo);
        }
    }

    @MockBean
    private ProductRepo productRepo;

    @MockBean
    private StoreRepo storeRepo;

    @Autowired
    private ProductService service;

    private static Store store1;

    private static Product product;

    @BeforeAll
    public static void setUp() {
        store1 = new Store(50, "Sorcerous Sundries", "SorcerousSundries@gmail.com", "+44 920350022", "password", null,
                new ArrayList<Product>());
        product = new Product(1, "Bag of Holding", 499.99f, 3, null);
    }

    @BeforeEach
    public void setUpMocks() {
        when(storeRepo.findById(store1.getId())).thenReturn(Optional.of(store1));
        when(storeRepo.findById(store1.getId() - 1)).thenReturn(Optional.empty());

        when(productRepo.findByProductIdAndStore(product.getProductId(), store1)).thenReturn(Optional.of(product));
        when(productRepo.findByProductIdAndStore(product.getProductId() - 1, store1)).thenReturn(Optional.empty());
    }

    // Create
    @Test
    public void createProduct_ExistantStore() {
        when(productRepo.save(product)).thenReturn(product);

        assertEquals(product, service.createProduct(store1.getId(), product));
    }

    @Test
    public void createProduct_NonExistantStore() {
        StoreException ex = assertThrows(StoreException.class,
                () -> {
                    service.createProduct(store1.getId() - 1, product);
                });
        assertTrue(ex.getMessage().contains(StoreExceptionMessages.STORE_NOT_FOUND));
    }

    // Read
    @Test
    public void getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepo.findAll()).thenReturn(products);

        assertTrue(service.getAllProducts().contains(product));
    }

    @Test
    public void getAllProductsByStore_ExistantStore() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepo.findByStore(store1)).thenReturn(products);

        assertTrue(service.getAllProductsByStore(store1.getId()).contains(product));
    }

    @Test
    public void getAllProductsByStore_NonExistantStore() {
        StoreException ex = assertThrows(StoreException.class,
                () -> {
                    service.getAllProductsByStore(store1.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(StoreExceptionMessages.STORE_NOT_FOUND));
    }

    @Test
    public void getSingleProduct_ExistantStoreAndProduct() {
        assertEquals(product, service.getSingleProduct(store1.getId(), product.getProductId()));
    }

    @Test
    public void getSingleProduct_ExistantStoreNotProduct() {
        ProductException ex = assertThrows(ProductException.class,
                () -> {
                    service.getSingleProduct(store1.getId(), product.getProductId() - 1);
                });
        assertTrue(ex.getMessage().contains(ProductExceptionMessages.PRODUCT_NOT_FOUND(store1.getId())));
    }

    @Test
    public void getSingleProduct_NonExistantStoreNotProduct() {
        StoreException ex = assertThrows(StoreException.class,
                () -> {
                    service.getSingleProduct(store1.getId() - 1, product.getProductId());
                });
        assertTrue(ex.getMessage().contains(StoreExceptionMessages.STORE_NOT_FOUND));
    }

    // Update
    @Test
    public void updateProduct_ExistantStoreAndProduct() {
        when(productRepo.save(product)).thenReturn(product);

        assertEquals(product, service.updateProduct(store1.getId(), product));
    }

    @Test
    public void updateProduct_ExistantStoreNotProduct() {
        product.setProductId(product.getProductId() - 1);

        ProductException ex = assertThrows(ProductException.class,
                () -> {
                    service.updateProduct(store1.getId(), product);
                });
        assertTrue(ex.getMessage().contains(ProductExceptionMessages.PRODUCT_NOT_FOUND(store1.getId())));

        product.setProductId(product.getProductId() + 1);
    }

    @Test
    public void updateProduct_NonExistantStoreNotProduct() {
        StoreException ex = assertThrows(StoreException.class,
                () -> {
                    service.updateProduct(store1.getId() - 1, product);
                });
        assertTrue(ex.getMessage().contains(StoreExceptionMessages.STORE_NOT_FOUND));
    }

    // Delete
    @Test
    public void deleteProduct_ExistantStoreAndProduct() {
        service.deleteProduct(store1.getId(), product.getProductId());

        verify(productRepo, times(1)).deleteById(product.getProductId());
    }

    @Test
    public void deleteProduct_ExistantStoreNotProduct() {
        ProductException ex = assertThrows(ProductException.class,
                () -> {
                    service.deleteProduct(store1.getId(), product.getProductId() - 1);
                });
        assertTrue(ex.getMessage().contains(ProductExceptionMessages.PRODUCT_NOT_FOUND(store1.getId())));

        verify(productRepo, times(0)).deleteById(anyInt());
    }

    @Test
    public void deleteProduct_NonExistantStoreNotProduct() {
        StoreException ex = assertThrows(StoreException.class,
                () -> {
                    service.deleteProduct(store1.getId() - 1, product.getProductId());
                });
        assertTrue(ex.getMessage().contains(StoreExceptionMessages.STORE_NOT_FOUND));

        verify(productRepo, times(0)).deleteById(anyInt());
    }
}
