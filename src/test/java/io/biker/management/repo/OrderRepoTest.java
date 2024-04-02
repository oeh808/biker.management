package io.biker.management.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.repo.BikerRepo;
import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.repo.CustomerRepo;
import io.biker.management.enums.OrderStatus;
import io.biker.management.order.entity.Order;
import io.biker.management.order.entity.OrderDetails;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.product.entity.Product;
import io.biker.management.product.repo.ProductRepo;
import io.biker.management.store.entity.Store;
import io.biker.management.store.repo.StoreRepo;
import io.biker.management.user.Address;

@ActiveProfiles("test")
@DataMongoTest
public class OrderRepoTest {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private StoreRepo storeRepo;

    @Autowired
    private BikerRepo bikerRepo;

    @Autowired
    private ProductRepo productRepo;

    private static Customer customer1;
    private static Customer customer2;

    private static Store store1;
    private static Store store2;

    private static Product product1;
    private static Product product2;

    private static Biker biker;

    private static Address address;

    private static OrderDetails orderDetails1;
    private static OrderDetails orderDetails2;

    private Order order1;
    private Order order2;

    @BeforeAll
    public static void setUp() {
        customer1 = new Customer(50, "Volo", "BardMan", "+44 770820695", "password");
        customer2 = new Customer(51, "Blah", "Blah@Blah-Blah", "202301924", "blahword");

        store1 = new Store(50, "Sorcerous Sundries", "SorcerousSundries@gmail.com", "+44 920350022",
                "password", null, new ArrayList<Product>());
        store2 = new Store(51, "The Blushing Mermaid", "TheBlushingMermaid@gmail.com", "+44 920350322",
                "password", null, new ArrayList<Product>());

        biker = new Biker(1, "Timmy", "Timmyyy@gmail.com", "+1512 3514000", "password", null);

        address = new Address("Basilisk Gate", "Baldur's Gate", "N/A", "B73 G22", "Faerun");

    }

    @BeforeEach
    public void setUpForEach() {
        customer1 = customerRepo.save(customer1);
        customer2 = customerRepo.save(customer2);

        store1 = storeRepo.save(store1);
        store2 = storeRepo.save(store2);

        product1 = new Product(1, "Bag of Holding", 499.99f, 100, store1);
        product2 = new Product(2, "Ale", 9.99f, 100, store2);

        product1 = productRepo.save(product1);
        product2 = productRepo.save(product2);

        orderDetails1 = new OrderDetails(product1, 0.14f, 569.90f, address, null);
        orderDetails2 = new OrderDetails(product2, 0.24f, 12.40f, address, null);

        biker = bikerRepo.save(biker);

        order1 = new Order(1, customer1, store1, null, OrderStatus.AWAITING_APPROVAL,
                java.sql.Date.valueOf("2050-09-15"), orderDetails1);
        order2 = new Order(2, customer1, store2, biker, OrderStatus.EN_ROUTE,
                java.sql.Date.valueOf("2023-05-13"), orderDetails2);

        order1 = orderRepo.save(order1);
        order2 = orderRepo.save(order2);
    }

    @AfterEach
    public void tearDownForEach() {
        orderRepo.deleteAll();
        productRepo.deleteAll();
        bikerRepo.deleteAll();
        storeRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    public void findByOrderIdAndCustomer_ExistantOrderIdAndCustomer() {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndCustomer(order1.getOrderId(), customer1);

        assertTrue(opOrder.isPresent());
        assertEquals(order1, opOrder.get());
    }

    @Test
    public void findByOrderIdAndCustomer_ExistantOrderIdWrongCustomer() {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndCustomer(order1.getOrderId(), customer2);

        assertTrue(opOrder.isEmpty());
    }

    @Test
    public void findByOrderIdAndCustomer_NonExistantOrderIdNotCustomer() {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndCustomer(order1.getOrderId() - 1, customer1);

        assertTrue(opOrder.isEmpty());
    }

    @Test
    public void findByOrderIdAndBiker_ExistantOrderIdAndBiker() {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndBiker(order2.getOrderId(), biker);

        assertTrue(opOrder.isPresent());
        assertEquals(order2, opOrder.get());
    }

    @Test
    public void findByOrderIdAndBiker_ExistantOrderIdWrongBiker() {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndBiker(order1.getOrderId(), biker);

        assertTrue(opOrder.isEmpty());
    }

    @Test
    public void findByOrderIdAndBiker_NonExistantOrderIdNotBiker() {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndBiker(order1.getOrderId() - 1, biker);

        assertTrue(opOrder.isEmpty());
    }

    @Test
    public void findByOrderIdAndStore_ExistantOrderIdAndStore() {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndStore(order1.getOrderId(), store1);

        assertTrue(opOrder.isPresent());
        assertEquals(order1, opOrder.get());
    }

    @Test
    public void findByOrderIdAndStore_ExistantOrderIdWrongStore() {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndStore(order1.getOrderId(), store2);

        assertTrue(opOrder.isEmpty());
    }

    @Test
    public void findByOrderIdAndStore_NonExistantOrderIdNotStore() {
        Optional<Order> opOrder = orderRepo.findByOrderIdAndStore(order1.getOrderId() - 1, store1);

        assertTrue(opOrder.isEmpty());
    }

    @Test
    public void findByStatus_Existant() {
        List<Order> orders = orderRepo.findByStatus(OrderStatus.AWAITING_APPROVAL);

        assertEquals(1, orders.size());
        assertTrue(orders.contains(order1));
    }

    @Test
    public void findByStore_Existant() {
        List<Order> orders = orderRepo.findByStore(store1);

        assertEquals(1, orders.size());
        assertTrue(orders.contains(order1));
    }

    @Test
    public void findByBiker_Existant() {
        List<Order> orders = orderRepo.findByBiker(biker);

        assertEquals(1, orders.size());
        assertTrue(orders.contains(order2));
    }
}
