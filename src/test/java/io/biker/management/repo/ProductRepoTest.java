package io.biker.management.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import io.biker.management.product.entity.Product;
import io.biker.management.product.repo.ProductRepo;
import io.biker.management.store.entity.Store;
import io.biker.management.store.repo.StoreRepo;

@ActiveProfiles("test")
@DataMongoTest
public class ProductRepoTest {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private StoreRepo storeRepo;

    private static Store store1;
    private static Store store2;

    private static Product product1;
    private static Product product2;

    @BeforeAll
    public static void setUp() {
        store1 = new Store(50, "Sorcerous Sundries", "SorcerousSundries@gmail.com", "+44 920350022",
                "password", null, new ArrayList<Product>());
        store2 = new Store(51, "The Blushing Mermaid", "TheBlushingMermaid@gmail.com", "+44 920350322",
                "password", null, new ArrayList<Product>());
        product1 = new Product(1, "Brown Bag", 49.99f, 20, null);
        product2 = new Product(2, "Bag of Holding", 499.99f, 3, null);
    }

    @BeforeEach
    public void setUpForEach() {
        store1 = storeRepo.save(store1);
        store2 = storeRepo.save(store2);

        product1.setStore(store1);
        product2.setStore(store1);

        product1 = productRepo.save(product1);
        product2 = productRepo.save(product2);
    }

    @AfterEach
    public void tearDownForEach() {
        productRepo.deleteAll();
        storeRepo.deleteAll();
    }

    @Test
    public void findByProductIdAndStore_ExistantStoreAndProduct() {
        Optional<Product> opProduct = productRepo.findByProductIdAndStore(product1.getProductId(), store1);

        assertTrue(opProduct.isPresent());
        assertEquals(product1, opProduct.get());
    }

    @Test
    public void findByProductIdAndStore_ExistantStoreNotProduct() {
        Optional<Product> opProduct = productRepo
                .findByProductIdAndStore(product1.getProductId() + product2.getProductId(), store1);
        assertTrue(opProduct.isEmpty());
    }

    @Test
    public void findByProductIdAndStore_WrongStore() {
        Optional<Product> opProduct = productRepo
                .findByProductIdAndStore(product1.getProductId(), store2);
        assertTrue(opProduct.isEmpty());
    }

    @Test
    public void findByStore_Existant() {
        List<Product> products = productRepo.findByStore(store1);

        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    public void findByStore_WrongStore() {
        List<Product> products = productRepo.findByStore(store2);

        assertTrue(products.isEmpty());
    }
}
