package io.biker.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
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

import io.biker.management.biker.entity.Biker;
import io.biker.management.customer.entity.Customer;
import io.biker.management.order.constants.OrderStatus;
import io.biker.management.order.entity.FeedBack;
import io.biker.management.order.entity.Order;
import io.biker.management.order.entity.OrderDetails;
import io.biker.management.order.exception.OrderException;
import io.biker.management.order.exception.OrderExceptionMessages;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.order.service.OrderService;
import io.biker.management.order.service.OrderServiceImpl;
import io.biker.management.product.entity.Product;
import io.biker.management.store.entity.Store;
import io.biker.management.user.Address;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class OrderServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        OrderService service(OrderRepo repo) {
            return new OrderServiceImpl(repo);
        }
    }

    @MockBean
    private OrderRepo repo;

    @Autowired
    private OrderService service;

    private static Customer customer;
    private static Store store;
    private static Product product;
    private static Biker biker;

    private static Address address;
    private static OrderDetails orderDetails;

    private static Order order;

    @BeforeAll
    public static void setUp() {
        customer = new Customer(50, "Volo", "BardMan", "+44 770820695", "password");
        store = new Store(50, "Sorcerous Sundries", "SorcerousSundries@gmail.com", "+44 920350022",
                "password", null, new ArrayList<Product>());
        product = new Product(2, "Bag of Holding", 499.99f, 3, store);
        biker = new Biker(1, "Timmy", "Timmyyy@gmail.com", "+1512 3514000", "password", null);

        address = new Address("Basilisk Gate", "Baldur's Gate", "N/A", "B73 G22", "Faerun");
        orderDetails = new OrderDetails(product.getName(), product.getPrice(), 0.14f, (product.getPrice() * 1.14f),
                address, null);

        order = new Order(0, customer, store, biker, OrderStatus.AWAITING_APPROVAL, Date.valueOf("2050-09-15"),
                orderDetails);
    }

    @BeforeEach
    public void setUpMocks() {
        order.setStatus(OrderStatus.AWAITING_APPROVAL);
        order.setEta(Date.valueOf("2050-09-15"));
        order.setBiker(biker);

        when(repo.findById(order.getOrderId())).thenReturn(Optional.of(order));
        when(repo.findById(order.getOrderId() - 1)).thenReturn(Optional.empty());

        when(repo.findByOrderIdAndCustomer(order.getOrderId(), customer)).thenReturn(Optional.of(order));
        when(repo.findByOrderIdAndCustomer(order.getOrderId() - 1, customer)).thenReturn(Optional.empty());

        when(repo.findByOrderIdAndBiker(order.getOrderId(), biker)).thenReturn(Optional.of(order));
        when(repo.findByOrderIdAndBiker(order.getOrderId() - 1, biker)).thenReturn(Optional.empty());

        when(repo.findByOrderIdAndStore(order.getOrderId(), store)).thenReturn(Optional.of(order));
        when(repo.findByOrderIdAndStore(order.getOrderId() - 1, store)).thenReturn(Optional.empty());
    }

    @Test
    public void createOrder() {
        doAnswer((i) -> {
            Order createdOrder = i.getArgument(0);
            return createdOrder;
        }).when(repo).save(any(Order.class));

        Order createdOrder = service.createOrder(customer, product, address);

        assertEquals(order.getCustomer(), createdOrder.getCustomer());
        assertEquals(product.getName(), createdOrder.getOrderDetails().getProductName());
        assertEquals(address, createdOrder.getOrderDetails().getAddress());
        assertEquals(OrderStatus.AWAITING_APPROVAL, createdOrder.getStatus());
    }

    @Test
    public void getOrder_Success() {
        assertEquals(order, service.getOrder(customer, order.getOrderId()));
    }

    @Test
    public void getOrder_NotFound() {
        OrderException ex = assertThrows(OrderException.class,
                () -> {
                    service.getOrder(customer, order.getOrderId() - 1);
                });
        assertTrue(ex.getMessage().contains(OrderExceptionMessages.ORDER_NOT_FOUND));
    }

    @Test
    public void getOrder_BackOffice_Success() {
        assertEquals(order, service.getOrder_BackOffice(order.getOrderId()));
    }

    @Test
    public void getOrder_BackOffice_NotFound() {
        OrderException ex = assertThrows(OrderException.class,
                () -> {
                    service.getOrder_BackOffice(order.getOrderId() - 1);
                });
        assertTrue(ex.getMessage().contains(OrderExceptionMessages.ORDER_NOT_FOUND));
    }

    @Test
    public void getAvailableOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(repo.findByStatus(OrderStatus.AWAITING_APPROVAL)).thenReturn(orders);

        assertEquals(orders, service.getAvailableOrders());
    }

    @Test
    public void getOrdersByStore() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(repo.findByStore(store)).thenReturn(orders);

        assertEquals(orders, service.getOrdersByStore(store));
    }

    @Test
    public void getOrdersByBiker() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(repo.findByBiker(biker)).thenReturn(orders);

        assertEquals(orders, service.getOrdersByBiker(biker));
    }

    @Test
    public void rateOrder() {
        FeedBack feedBack = new FeedBack(5, "Amazing");

        service.rateOrder(customer, order.getOrderId(), feedBack);

        assertEquals(feedBack, order.getOrderDetails().getFeedBack());
        verify(repo, times(1)).save(order);
    }

    @Test
    public void updateOrderStatus_Biker() {
        service.updateOrderStatus_Biker(biker, order.getOrderId(), OrderStatus.DELIVERED);

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        verify(repo, times(1)).save(order);
    }

    @Test
    public void updateOrderStatus_Store() {
        service.updateOrderStatus_Store(store, order.getOrderId(), OrderStatus.DELIVERED);

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        verify(repo, times(1)).save(order);
    }

    @Test
    public void updateOrderStatus_BackOffice() {
        service.updateOrderStatus_BackOffice(order.getOrderId(), OrderStatus.DELIVERED);

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        verify(repo, times(1)).save(order);
    }

    @Test
    public void updateOrderEta_Biker() {
        Date eta = Date.valueOf("3050-09-15");
        service.updateOrderEta_Biker(biker, order.getOrderId(), eta);

        assertEquals(eta, order.getEta());
        verify(repo, times(1)).save(order);
    }

    @Test
    public void updateOrderEta_Store() {
        Date eta = Date.valueOf("3050-09-15");
        service.updateOrderEta_Store(store, order.getOrderId(), eta);

        assertEquals(eta, order.getEta());
        verify(repo, times(1)).save(order);
    }

    @Test
    public void updateOrderEta_BackOffice() {
        Date eta = Date.valueOf("3050-09-15");
        service.updateOrderEta_BackOffice(order.getOrderId(), eta);

        assertEquals(eta, order.getEta());
        verify(repo, times(1)).save(order);
    }

    @Test
    public void assignDelivery() {
        Biker someBiker = new Biker();
        service.assignDelivery(someBiker, order.getOrderId());

        assertEquals(someBiker, order.getBiker());
        verify(repo, times(1)).save(order);
    }

    @Test
    public void deleteOrder_Existant() {
        service.deleteOrder(order.getOrderId());

        verify(repo, times(1)).deleteById(order.getOrderId());
    }

    @Test
    public void deleteOrder_NonExistant() {
        OrderException ex = assertThrows(OrderException.class,
                () -> {
                    service.deleteOrder(order.getOrderId() - 1);
                });
        assertTrue(ex.getMessage().contains(OrderExceptionMessages.ORDER_NOT_FOUND));

        verify(repo, times(0)).deleteById(order.getOrderId() - 1);
    }
}
